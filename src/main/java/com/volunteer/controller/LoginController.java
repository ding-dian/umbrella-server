package com.volunteer.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.volunteer.component.RedisOperator;
import com.volunteer.component.TencentSmsOperator;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.exception.PhoneNumberInvalidException;
import com.volunteer.service.VolunteerService;

import com.volunteer.util.AES;
import com.volunteer.util.RegularUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author VernHe
 * @date 2021年12月14日 17:37
 */
@Api(tags ="小程序登录模块")
@Slf4j
@RestController
@RequestMapping("/miniProgram")
public class LoginController {

    @Value("${mini-program.appId}")
    private String appId;

    @Value("${mini-program.appSecret}")
    private String appSecret;

    @Value("${AES-secret}")
    private String AESSecret;


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
     * 检查登录状态
     * @param map
     * @return
     */
    @PostMapping("/checkLoginState")
    public Result checkLoginState(@RequestBody Map<String,String> map) {
        String token=map.get("token");
        if (StringUtils.isEmpty(token)) {
            return ResultGenerator.getFailResult("参数有误，请检查后重试");
        }
        String jsonStr = redisOperator.get(token);
        if (ObjectUtil.isNull(jsonStr)) {
            // token已过期
            return ResultGenerator.getSuccessResult(false);
        }
        // token未过期,刷新过期时间
        redisOperator.expire(token,7200);
        return ResultGenerator.getSuccessResult(true);
    }


    /**
     * 保存用户的电话
     * @param map 传入的信息有用户的token和手机号
     * @return
     */
    @PostMapping("/saveUserPhoneNumber")
    public Result saveUserPhoneNumber(@RequestBody Map<String,String> map) {
        String token=map.get("token");
        String phoneNumber=map.get("PhoneNumber");
        //从缓存中根据token获得用户信息
        String jsonStr = redisOperator.get(token);
        if(StringUtils.isEmpty(jsonStr)){
            return ResultGenerator.getFailResult("token有误");
        }
        JSONObject userInfo=JSONUtil.parseObj(jsonStr);
        //获得openID
        String openID=userInfo.getStr("openid");
        //参数校验
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(phoneNumber)) {
            return ResultGenerator.getFailResult("参数有误，请检查后重试");
        }
        //手机号为敏感信息需要先加密再存入数据库
        String encrypt;
        try {
            encrypt = AES.aesEncrypt(AES.base64Encode(AES.encrypt(phoneNumber, AESSecret)));
        } catch (Exception e) {
            log.info("异常信息：{}", e.getMessage());
            return ResultGenerator.getFailResult("加密失败");
        }
        //先检查手机号是否已经被绑定并且用户信息存在
        if(!volunteerService.phoneNumberIsBound(encrypt)&&volunteerService.getByOpenId(openID)!=null){
            //验证通过存入数据库
            UpdateWrapper<Volunteer> updateWrapper=new UpdateWrapper<>();
            updateWrapper.set("phone_number",encrypt);
            updateWrapper.eq("openID",openID);
            volunteerService.update(updateWrapper);
            return ResultGenerator.getSuccessResult();
        }else {
            return ResultGenerator.getFailResult("手机号已经被绑定或用户未授权");
        }
    }

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
        // 根据OpenId从数据库中获取数据,数据库中没有表示没有授权
        String openid = jsonObject.getStr("openid");
        Volunteer volunteer = volunteerService.getByOpenId(openid);
        // 如果用户没有授权过
        if (Objects.isNull(volunteer)) {
            log.info("用户不存在，openid:{}", openid);
            Result result = new Result();
            // 600表示没有授权
            result.setCode(600).setMessage("请授权后再登录");
            return result;
        }
        // 将志愿者信息存入Redis中
        String jsonStr = JSONUtil.parseObj(volunteer).toString();
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
            JSONObject codeResult = resolveCode(code);
            JSONObject userInfo = getUserInfoFromWx(codeResult.getStr("session_key"), rawData, signature);
            if (Objects.isNull(userInfo)) {
                return ResultGenerator.getFailResult("数据签名校验失败");
            }
            // 将用户信息保存至数据库中
            Volunteer volunteer = volunteerService.register(userInfo,codeResult.getStr("openid"));
            if (Objects.isNull(volunteer)) {
                return ResultGenerator.getFailResult("注册失败，请稍后重试");
            }
            String jsonStr = userInfo.toString();
            log.info("解析从微信服务器获得的用户信息:{}", jsonStr);
            // 将用户信息保存至redis中，然后返回token
            String token = DigestUtil.md5Hex(jsonStr);
            redisOperator.set(token,jsonStr);
            Map<String,String> data = new HashMap<>();
            data.put("token",token);
            data.put("userInfo", jsonStr);
            return ResultGenerator.getSuccessResult(data);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("异常信息：{}", e.getMessage());
            return ResultGenerator.getFailResult("服务器出现异常，请联系管理员");
        }
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
//            code = RandomUtil.randomNumbers(6);
            // 测试时使用下面的代码，将上面的代码注释，验证码写死了为：111111
            code = "111111";
        }
        try {
            // 发送验证码
//            if (smsOperator.sendSms(phoneNumber, code)) {
//                // 将验证码保存至Redis,手机号属于铭感信息，因此加密保存，而不是用明文
//                redisOperator.set(DigestUtil.md5Hex(phoneNumber), code, 60 * expires);
//                return ResultGenerator.getSuccessResult(code);
//            } else {
//                return ResultGenerator.getFailResult("验证码发送失败，请联系管理员");
//            }
            // 测试时用下面代码，然后上面的代码注释
            redisOperator.set(DigestUtil.md5Hex(phoneNumber), code, 60 * expires);
            return ResultGenerator.getSuccessResult(code);
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
     * @param signature1 微信发送的签名
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
