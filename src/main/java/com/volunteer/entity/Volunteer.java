package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Volunteer implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 志愿者ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private Long phoneNumber;

    /**
     * 邮箱
     */
    private String emailAddress;

    /**
     * 学号
     */
    private Long studentId;

    /**
     * 密码
     */
    private String password;

    /**
     * 学院
     */
    private String institude;

    /**
     * 年级
     */
    private Integer grade;

    /**
     * 专业
     */
    private String major;

    /**
     * 积分
     */
    private Integer points;

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

    /**
     * 页号
     */
    @TableField(exist = false)
    private int pageNo;

    /**
     * 每页大小
     */
    @TableField(exist = false)
    private int pageSize;
}
