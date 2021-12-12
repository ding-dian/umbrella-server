package com.volunteer.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author VernHe
 * @date 2021年11月20日 13:37
 * @description 用于接收登录时的用户名和密码
 */
@Data
@ApiModel(value = "表单对象",description = "用于接收登录时的用户名和密码")
public class LoginVo {

    @ApiModelProperty(name = "用户名", required = true, example = "admin")
    private String userName;

    @ApiModelProperty(name = "密码", required = true, example = "123456")
    private String password;
}
