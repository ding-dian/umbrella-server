package com.volunteer.entity.vo;

import lombok.Data;

/**
 * @author 何福任
 */
@Data
public class AdminVo {
    /**
     * 用户名
     */
    private String username;

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
     * 描述信息
     */
    private String description;
}
