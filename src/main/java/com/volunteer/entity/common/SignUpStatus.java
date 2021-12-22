package com.volunteer.entity.common;

/**
 * 报名状态
 *
 * @author VernHe
 * @date 2021年12月22日 14:50
 */
public class SignUpStatus {

    /**
     * 报名失败
     */
    public static final int SIGN_UP_FAIL = 0;

    /**
     * 报名成功
     */
    public static final int SIGN_UP_SUCCESS = 1;

    /**
     * 已经报名过了
     */
    public static final int ALREADY_SIGNED_UP = 2;

    /**
     * 未登录
     */
    public static final int NOT_LOGIN = 3;



}
