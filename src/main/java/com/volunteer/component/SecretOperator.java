package com.volunteer.component;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * AES加解密
 *
 * @author VernHe
 * @date 2021年12月23日 21:34
 */
@Component
public class SecretOperator {

    @Value("${AES-secret}")
    private String AESSecret;

    private final AES aes = SecureUtil.aes(Base64.decode(AESSecret));

    public String aesEncrypt(String data) {
        return aes.encryptHex(data);
    }

    public String aesDecrypt(String data) {
        return aes.decryptStr(data);
    }

}
