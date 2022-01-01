package com.volunteer.service;

import org.springframework.scheduling.annotation.Async;

/**
 * @author VernHe
 * @date 2021年12月31日 23:17
 */
public interface LoginService {
    @Async
    void sendSms(String phoneNumber, String code, long expires);
}
