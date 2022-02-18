package com.volunteer.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 何福任
 */
@ApiModel(value = "对象",description = "用于接收管理员对象")
@Data
public class AdminVo {
    /**
     * 用户名
     */
    @ApiModelProperty(required = true)
    private String username;

    /**
     * 头像
     */
    @ApiModelProperty(required = true)
    private String avatar;

    /**
     * 姓名
     */
    @ApiModelProperty(required = true)
    private String name;

    /**
     * 身份
     */
    @ApiModelProperty(required = true)
    private String roles;

    /**
     * 描述信息
     */
    @ApiModelProperty(required = true)
    private String description;
}
