package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
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
public class VolunteerStatisticalInformation implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 志愿者id
     */
    private Integer volunteerId;

    /**
     * 志愿者总时长
     */
    private BigDecimal volunteerDurations;

    /**
     * 志愿者总积分
     */
    @TableField("volunteer_points")
    private Integer volunteerPoints;

    private Integer activityNumbers;

    private LocalDateTime createAt;

    /**
     * 0未删除 1已删除
     */
    private Integer deleted;


}
