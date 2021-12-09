package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
public class AdminInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份
     */
    private String roles;

    /**
     * 【0】未删除 【1】已删除
     */
    private String deleted;

    /**
     * 描述信息
     */
    private String description;


}
