package com.volunteer.entity;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

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
public class VolunteerActivity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 志愿活动ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动名称
     */
    @TableField("activity_name")
    private String activityName;

    /**
     * 活动图片
     */
    @TableField("activity_img")
    private String activityImg;

    /**
     * 活动地点
     */
    @TableField("activity_address")
    private String activityAddress;

    /**
     * 活动所需人数
     */
    @TableField("number_of_need")
    private Integer numberOfNeed;

    /**
     * 活动参加人数
     */
    @TableField("number_of_attendees")
    private Integer numberOfAttendees;

    /**
     * 活动描述
     */
    @TableField("description")
    private String description;

    /**
     * 奖励积分
     */
    @TableField("reward_points")
    private Integer rewardPoints;

    /**
     * 活动开始时间
     */
    @TableField("start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

//    @TableField(exist = false)
//    private String startTimeStr;
    /**
     * 活动结束时间
     */
    @TableField("end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

//    @TableField(exist = false)
//    private  String endTimeStr;

    /**
     * 状态【00:进行中，01:未开始，02:已结束】
     */
    @TableField("status")
    private String status;

    /**
     * 逻辑删除【0:未删除，1:已删除】
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField("create_at")
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField("update_at")
    private LocalDateTime updateAt;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 【0:未审核，1:已审核】
     */
    @TableField("is_audited")
    private Integer isAudited;

    /**
     * 预计志愿活动时长
     */
    @TableField("predict_duration")
    private Double predictDuration;

    /**
     * 实际志愿活动时长
     */
    @TableField("actual_duration")
    private Double actualDuration;

    public void setStartTime(String startTime) {
        this.startTime = LocalDateTimeUtil.parse(startTime, "yyyy-MM-dd HH:mm:ss");
    }

    public void setEndTime(String endTime) {
        this.endTime = LocalDateTimeUtil.parse(endTime, "yyyy-MM-dd HH:mm:ss");
    }
}
