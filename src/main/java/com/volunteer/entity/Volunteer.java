package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Data
@ApiModel(value = "志愿者对象",description = "用来接收志愿者信息的实体" )
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Volunteer implements Serializable {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID=1L;

    /**
     * 志愿者ID
     */
    @ApiModelProperty(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户在小程序端唯一的ID
     */
    @ApiModelProperty(required = true,example = "openid")
    private String openid;

    /**
     * 姓名
     */
    @ApiModelProperty(required = false,example = "佩奇")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(required = false,example = "123456")
    private Long phoneNumber;

    /**
     * 邮箱
     */
    @ApiModelProperty(required = false,example = "xxxx@foxmail.com")
    private String emailAddress;

    /**
     * 学号
     */
    @ApiModelProperty(required = false ,example = "18020333222")
    private Long studentId;

    /**
     * 密码
     */
    @ApiModelProperty(required = false,example = "123456")
    private String password;

    /**
     * 学院
     */
    @ApiModelProperty(required = false,example = "计信")
    private String institude;

    /**
     * 年级
     */
    @ApiModelProperty(required = false,example = "2")
    private Integer grade;

    /**
     * 专业
     */
    @ApiModelProperty(required = false,example = "网络")
    private String major;

    /**
     * 昵称
     */
    @ApiModelProperty(required = false,example = "昵称")
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 性别
     */
    @ApiModelProperty(required = false,notes = "【0:未知,1:男,2:女】",example = "1")
    private Integer gender;

    /**
     * 头像
     */
    @ApiModelProperty(required = false,example = "https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eplWzPJuvFyWeY5KjG5mOv6a7YNHNDvyxCibSCv58iaxicjyjPD08FoicQDBibMoCF64urOjYFEicM5KTeQ/132")
    private String avatarUrl;


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
    @ApiModelProperty(hidden = true)
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

    /**
     * 页号
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private int pageNo;

    /**
     * 每页大小
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private int pageSize;
}
