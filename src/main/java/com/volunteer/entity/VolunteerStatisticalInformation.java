package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *  获取志愿者参加活动次数、志愿总时长等信息
 * </p>
 *
 * @author xiaoyao
 * @since 2021-11-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "志愿者信息统计对象", description = "用于接收志愿者信息的实体")
public class VolunteerStatisticalInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 志愿者id
     */
    @ApiModelProperty(name = "openID", value = "志愿者ID", required = true, example = "1")
    private Integer volunteerId;

    /**
     * 志愿者总时长
     */
    @ApiModelProperty(name = "volunteerDurations", value = "志愿者活动总时长", required = true, example = "4.5")
    @TableField("volunteer_durations")
    private BigDecimal volunteerDurations;

    /**
     * 志愿者总积分
     */
    @ApiModelProperty(name = "volunteerPoints", value = "志愿者活动总积分", required = true, example = "30")
    @TableField("volunteer_points")
    private Integer volunteerPoints;

    /**
     * 志愿者参加活动总次数
     */
    @ApiModelProperty(name = "activityNumbers", value = "志愿者参加活动总次数", required = true, example = "2")
    @TableField("activity_numbers")
    private Integer activityNumbers;

    /**
     * 该条记录创建时间
     */
    @ApiModelProperty(name = "createAt", value = "该条记录创建时间", required = true, example = "2021-12-13 13:54:50")
    @TableField("create_at")
    private LocalDateTime createAt;

    /**
     * 用户借取雨伞总时长
     */
    @ApiModelProperty(name = "umbrellaBorrowDurations", value = "用户借取雨伞总时长", required = true, example = "7.5")
    @TableField("umbrella_borrow_durations")
    private BigDecimal umbrellaBorrowDurations;


    /**
     * 0未删除 1已删除
     */
    @ApiModelProperty(name = "deleted", value = "0未删除 1已删除", required = true, example = "0")
    @TableField("deleted")
    private Integer deleted;


}
