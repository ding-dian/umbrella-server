package com.volunteer.entity.vo;

import lombok.Data;

/**
 * @author VernHe
 * @date 2021年11月20日 13:37
 * @description 用于接收登录时的用户名和密码
 */
@Data
public class LoginVo {

    private String userName;

    private String password;
}
