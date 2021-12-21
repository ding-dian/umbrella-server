package com.volunteer.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.volunteer.component.RedisOperator;
import com.volunteer.component.TencentSmsOperator;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.exception.PhoneNumberInvalidException;
import com.volunteer.service.VolunteerService;
import com.volunteer.util.RegularUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author VernHe
 * @date 2021年12月14日 17:37
 */

@Slf4j
@RestController
@RequestMapping("/miniProgram")
public class VolunteerLoginController {

    @Value("${mini-program.appId}")
    private String appId;

    @Value("${mini-program.appSecret}")
    private String appSecret;

    /**
     * 验证码的有效时间，单位是分钟
     */
    @Value("${tencent-cloud.expires}")
    private Integer expires;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private TencentSmsOperator smsOperator;

    /**
     * 小程序端登录,获取token
     *
     * @param map 传入code
     * @return token
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> map) {
        String code = map.get("code");
        if (StringUtils.isEmpty(code)) {
            return ResultGenerator.getFailResult("code参数有误!");
        }
        JSONObject jsonObject = resolveCode(code);
        // 根据OpenId从数据库中获取数据
        String openid = jsonObject.getStr("openid");
        Volunteer volunteer = volunteerService.getByOpenId(openid);
        if (Objects.isNull(volunteer)) {
            log.info("用户不存在，openid:{}", openid);
            return ResultGenerator.getFailResult("用户未授权，请在授权后重试");
        }
        // 将志愿者信息存如Redis中
        String jsonStr = JSONUtil.parseObj(volunteer).toStringPretty();
        String token = DigestUtil.md5Hex(jsonStr);
        redisOperator.set(token, jsonStr, 7200);
        return ResultGenerator.getSuccessResult(token);
    }

    /**
     * 根据Token去获取用户信息
     *
     * @param token
     * @return
     */
    @GetMapping("/getInfo")
    public Result getUserInfoByToken(@RequestParam String token) {
        if (StringUtils.isEmpty(token)) {
            return ResultGenerator.getFailResult("token有误!");
        }
        String jsonStr = redisOperator.get(token);
        if (StringUtils.isEmpty(jsonStr)) {
            return ResultGenerator.getFailResult("请登录后重试");
        }
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    /**
     * 如果未授权，则需要授权后再注册
     *
     * @return
     * @Param code, rawData, signature
     */
    @PostMapping("/registry")
    public Result registry(@RequestBody Map<String, String> map) {
        // 参数校验
        String code = map.get("code");
        String rawData = map.get("rawData");
        String signature = map.get("signature");
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(rawData) || StringUtils.isEmpty(signature)) {
            return ResultGenerator.getFailResult("参数有误，请检查后重试");
        }
        try {
            JSONObject userInfo = getUserInfoFromWx(resolveCode(code).getStr("session_key"), rawData, signature);
            if (Objects.isNull(userInfo)) {
                return ResultGenerator.getFailResult("签名校验失败");
            }
            // 将用户信息保存至数据库中
            Volunteer volunteer = volunteerService.register(userInfo);
            if (Objects.isNull(volunteer)) {
                return ResultGenerator.getFailResult("志愿者添加失败");
            }
            log.info("解析得到的用户信息:{}", userInfo.toStringPretty());
            return ResultGenerator.getSuccessResult(volunteer);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("异常信息：{}", e.getMessage());
        }
        return ResultGenerator.getFailResult("服务器出现异常，请联系管理员");
    }

    /**
     * 验证码发送接口
     * @param map
     * @return  code 验证码
     */
    @PostMapping("/sendCode")
    public Result senSms(@RequestBody Map<String, String> map) {
        // 获取并校验参数
        String phoneNumber = map.get("phone");
        if (StringUtils.isEmpty(phoneNumber) || !RegularUtil.checkPhoneNumber(phoneNumber)) {
            return ResultGenerator.getFailResult("手机号有误，请检查后重试");
        }
        // 校验是否已经被绑定过
        if (volunteerService.phoneNumberIsBound(phoneNumber)) {
            return ResultGenerator.getFailResult("该手机号已被绑定");
        }
        // 检查验证码是否已经过期，如果没有过期则继续使用，而不是再次生成
        String code = redisOperator.get(DigestUtil.md5Hex(phoneNumber));
        if (StringUtils.isEmpty(code)) {
            // 生成6位的验证码
            code = RandomUtil.randomNumbers(6);
        }
        try {
            // 发送验证码
            if (smsOperator.sendSms(phoneNumber, code)) {
                // 将验证码保存至Redis,手机号属于铭感信息，因此加密保存，而不是用明文
                redisOperator.set(DigestUtil.md5Hex(phoneNumber), code, 60 * expires);
                return ResultGenerator.getSuccessResult(code);
            } else {
                return ResultGenerator.getFailResult("验证码发送失败，请联系管理员");
            }
        } catch (PhoneNumberInvalidException e) {
            // 手机号格式错误
            return ResultGenerator.getFailResult(e.getMessage());
        } catch (Exception e) {
            // 其他原因发送失败
            log.error(e.getMessage());
            return ResultGenerator.getFailResult("验证码发送失败，请联系管理员");
        }
    }

    /**
     * 从微信端获取用户信息
     *
     * @param session_key
     * @param rawData
     * @param signature1
     * @return
     */
    private JSONObject getUserInfoFromWx(String session_key, String rawData, String signature1) {
        String signature2 = DigestUtil.sha1Hex(rawData + session_key);
        // 校验签名
        log.info("rawData:{},session_key:{}", rawData, session_key);
        log.info("计算得到的签名：{},微信发送的签名：{}", signature2, signature1);
        if (!signature1.equals(signature2)) {
            log.info("签名校验失败");
            return null;
        }
        log.info("签名校验成功");
        // 业务逻辑
        JSONObject userInfo = JSONUtil.parseObj(rawData);
        return userInfo;
    }

    /**
     * 根据Code从微信端获取OpenId（小程序用户的唯一表示）和 session_key
     *
     * @return
     */
    private JSONObject resolveCode(String code) {
        // GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String path = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        log.info("code:{},appid:{},appSecret:{}", code, appId, appSecret);
        // {"session_key":"oWcaQF82p6+Ogk\/0Pd1A7Q==","openid":"ozLwX5aDHI2DtkhZZWo7djNnEtls"}
        String jsonStr = HttpUtil.get(path);
        log.info("返回的数据：{}", jsonStr);
        return JSONUtil.parseObj(jsonStr);
    }

}
