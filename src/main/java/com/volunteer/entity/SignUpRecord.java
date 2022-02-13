package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.Api;
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
@EqualsAndHashCode(callSuper = false)
// 可以链式调用
@Accessors(chain = true)
@ApiModel(value = "报名活动对象",description = "用于接收志愿者报名活动实体")
public class SignUpRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 报名记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * 志愿者ID
     */
    @ApiModelProperty(required = true)
    private Integer volunteerId;

    /**
     * 活动ID
     */
    @ApiModelProperty(required = true)
    private Integer volunteerActivityId;

    /**
     * 逻辑删除【0:未删除，1:已删除】
     */
    @ApiModelProperty(hidden = true)
    private Integer deleted;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime createAt;

    /**
     * 是否签到【0:未签到，1:已签到】
     */
    @ApiModelProperty(hidden = true)
    private Integer isSignIn;

}
