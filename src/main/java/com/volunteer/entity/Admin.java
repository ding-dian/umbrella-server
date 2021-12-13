package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author VernHe
 * @date 2021年11月20日 13:33
 */
@Data
@ApiModel(value = "用户对象",description = "用于接收用户的实体")
public class Admin {

    /**
     * id
     */
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty(required = true)
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(required = true)
    private String password;

    /**
     * 头像
     */
    @ApiModelProperty(required = true)
    private String avatar;

    /**
     * 描述
     */
    @ApiModelProperty(required = true)
    private String description;

    /**
     * 角色，如["admin","user"]，用于前端权限校验
     */
    @ApiModelProperty(required = true)
    private String[] roles;

    /**
     * 逻辑删除【0:未删除，1:已删除】
     */
    @TableLogic
    @ApiModelProperty(hidden = true)
    private Integer deleted;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    @ApiModelProperty(required = true)
    private String createBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime updateAt;

    /**
     * 更新人
     */
    @ApiModelProperty(hidden = true)
    private String updateBy;
}
