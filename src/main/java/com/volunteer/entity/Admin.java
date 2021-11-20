package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author VernHe
 * @date 2021年11月20日 13:33
 */
@Data
public class Admin {

    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 描述
     */
    private String description;

    /**
     * 角色，如["admin","user"]，用于前端权限校验
     */
    private String[] roles;

    /**
     * 逻辑删除【0:未删除，1:已删除】
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

    /**
     * 更新人
     */
    private String updateBy;
}
