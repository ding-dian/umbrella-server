package com.volunteer.exception;

/**
 * 手机号校验失败时抛出的异常
 *
 * @author VernHe
 * @date 2021年12月21日 11:07
 */
public class PhoneNumberInvalidException extends RuntimeException{

    public PhoneNumberInvalidException(String message) {
        super(message);
    }
}
