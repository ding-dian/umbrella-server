package com.volunteer.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.volunteer.component.RedisOperator;
import com.volunteer.component.TencentSmsOperator;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author VernHe
 * @date 2021年12月31日 23:18
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TencentSmsOperator smsOperator;

    @Autowired
    private RedisOperator redisOperator;

    @Override
    public void sendSms(String phoneNumber, String code, long expires) throws Exception {
        if (smsOperator.sendSms(phoneNumber, code)) {
            // 将验证码保存至Redis,手机号属于铭感信息，因此加密保存，而不是用明文
            redisOperator.set(DigestUtil.md5Hex(phoneNumber), code, 60 * expires);
            log.info("短信发送成功");
        } else {
            log.error("短信发送失败");
            throw new RuntimeException("短信发送失败");
        }
    }
}
