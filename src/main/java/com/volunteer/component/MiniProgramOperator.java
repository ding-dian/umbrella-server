package com.volunteer.component;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author VernHe
 * @date 2021年12月16日 23:20
 */
@Component
@Slf4j
public class MiniProgramOperator {

    @Value("${mini-program.appId}")
    private String appId;

    @Value("${mini-program.appSecret}")
    private String appSecret;

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 获取access_token
     * @return
     */
    public String getAccessToken() {
        String access_token = redisOperator.get("access_token");
        // 如果没有或者过期了就从微信端重新获取，然后存入Redis中
        if (StringUtils.isEmpty(access_token)) {
            JSONObject jsonObject = getAccessTokenFromWx();
            if (Objects.nonNull(jsonObject)) {
                access_token = jsonObject.getStr("access_token");
                redisOperator.set("access_token", access_token, jsonObject.getInt("expires_in"));
                return access_token;
            } else {
                log.warn("access_token 获取失败");
                return null;
            }
        }
        log.info("从缓存中获取Redis");
        return access_token;
    }

    /**
     * 从微信获取AccessToken
     * @return
     * {
     *     "access_token": "52_05QP-Cybl6pTRZVz2KqHrT4c3sk2RimY5-Qj_i2EKVuuwi_UaHfAQpmlC_yk02WJD2qaWS-VSTVu8Ovcezj7M4fUaZD46eCB-qf8gEgcERG8KshZHQz9OG_purEn-x7ZiOgtLvErIs6hhfJNOIPgACABVC",
     *     "expires_in": 7200
     * }
     */
    private JSONObject getAccessTokenFromWx() {
        String path = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
        String jsonStr = HttpUtil.get(path);
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        Integer errCode = jsonObject.getInt("errcode");
        if (Objects.nonNull(errCode)) {
            if (errCode == 0) {
                log.error("请求发送成功");
            } else if (errCode == -1) {
                log.error("系统繁忙，此时请开发者稍候再试");
            } else if (errCode == 40001) {
                log.error("AppSecret 错误或者 AppSecret 不属于这个小程序，请开发者确认 AppSecret 的正确性");
            }
            log.error("获取AccessToken时的错误信息:{}",jsonObject.getStr("errmsg"));
            return null;
        }
        return jsonObject;
    }


    /**
     * 根据Code从微信端获取OpenId（小程序用户的唯一表示）和 session_key
     * @return  {"session_key":"oWcaQF82p6+Ogk\/0Pd1A7Q==","openid":"ozLwX5aDHI2DtkhZZWo7djNnEtls"}
     */
    public JSONObject resolveCode(String code) {
        // GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String path = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        log.info("code:{},appid:{},appSecret:{}",code,appId,appSecret);
        String jsonStr = HttpUtil.get(path);
        log.info("解析code后得到的数据：{}",jsonStr);
        return JSONUtil.parseObj(jsonStr);
    }

    public String getPhoneNumber(String code) {
        String accessToken = getAccessToken();
        log.info("当前accessToken：{}",accessToken);
        if (StringUtils.isNotEmpty(accessToken)) {
            String path = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
            Map params = new HashMap(2);
            params.put("code",code);
            String res = HttpUtil.post(path, params);
            log.info(res);
            // {"errcode":47001,"errmsg":"data format error hint: [A.iFN8ore-wKxRIa] rid: 61bb6300-1afe0d69-29186878"}
        }
        return null;
    }


}
