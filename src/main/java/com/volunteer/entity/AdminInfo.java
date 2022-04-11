package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaoyao
 * @since 2021-12-06
 */

@Data
@ApiModel(value = "管理员对象",description = "用来接收管理员信息的实体" )
@EqualsAndHashCode(callSuper = false)
public class AdminInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty(required = true)
    private String username;

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
     * 【0】未删除 【1】已删除
     */
    @ApiModelProperty(hidden = true)
    private String deleted;

    /**
     * 描述信息
     */
    @ApiModelProperty(required = true)
    private String description;


}
