package com.volunteer.entity.vo;

import com.volunteer.entity.VolunteerActivity;
import lombok.Data;

import java.time.ZoneOffset;

/**
 * 志愿活动VO对象
 */
@Data
public class ActivityVo {
    /**
     * 志愿活动ID
     */
    private Integer id;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动图片
     */
    private String activityImg;

    /**
     * 活动地点
     */
    private String activityAddress;

    /**
     * 活动所需人数
     */
    private Integer numberOfNeed;

    /**
     * 活动参加人数
     */
    private Integer numberOfAttendees;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 奖励积分
     */
    private Integer rewardPoints;

    /**
     * 活动开始时间
     */
    private long startTime;

    /**
     * 活动结束时间
     */
    private long endTime;

    /**
     * 状态【00:进行中，01:未开始，02:已结束】
     */
    private String status;

    /**
     * 【0:未审核，1:已审核】
     */
    private Integer isAudited;

    /**
     * 预计志愿活动时长
     */
    private Double predictDuration;

    /**
     * 实际志愿活动时长
     */
    private Double actualDuration;

    public ActivityVo(VolunteerActivity activity) {
        this.id = activity.getId();
        this.activityName = activity.getActivityName();
        this.activityImg = activity.getActivityImg();
        this.activityAddress = activity.getActivityAddress();
        this.numberOfNeed = activity.getNumberOfNeed();
        this.numberOfAttendees = activity.getNumberOfAttendees();
        this.description = activity.getDescription();
        this.rewardPoints = activity.getRewardPoints();
        this.startTime = activity.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        this.endTime = activity.getEndTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        this.status = activity.getStatus();
        this.isAudited = activity.getIsAudited();
        this.predictDuration = activity.getPredictDuration();
        this.actualDuration = activity.getActualDuration();
    }
}
