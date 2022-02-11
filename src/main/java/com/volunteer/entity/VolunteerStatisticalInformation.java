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
 *
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

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 志愿者id
     */
    private Integer volunteerId;

    /**
     * 志愿者总时长
     */
    @ApiModelProperty(hidden = true)
    private BigDecimal volunteerDurations;

    /**
     * 志愿者总积分
     */
    @TableField("volunteer_points")
    @ApiModelProperty(hidden = true)
    private Integer volunteerPoints;

    @ApiModelProperty(hidden = true)
    private Integer activityNumbers;

    @ApiModelProperty(hidden = true)
    private LocalDateTime createAt;

    /**
     * 0未删除 1已删除
     */
    @ApiModelProperty(hidden = true)
    private Integer deleted;

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
