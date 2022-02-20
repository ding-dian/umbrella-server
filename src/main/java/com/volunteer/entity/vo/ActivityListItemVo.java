package com.volunteer.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ActivityListItemVo {

    /**
     * 活动id
     */
    private Integer activityId;

    /**
     * 图片url
     */
    private String imgUrl;

    /**
     * 活动标题
     */
    private String activityTitle;

    /**
     * 活动详情介绍
     */
    private String description;

    /**
     * 招募进度
     */
    private Integer percent;

    /**
     * 报名人数
     */
    private Integer registrationPopulation;

    /**
     * 志愿时长
     */
    private Double predictDuration;

    /**
     * 剩余名额
     */
    private Integer remainingPlaces;

    public ActivityListItemVo() {}

    public ActivityListItemVo(ActivityVo vo) {
        this.activityId = vo.getId();
        this.imgUrl = vo.getActivityImg();
        this.activityTitle = vo.getActivityName();
        this.description = vo.getDescription();
        this.percent = vo.getNumberOfNeed();
        this.registrationPopulation = vo.getNumberOfAttendees();
        this.predictDuration = vo.getPredictDuration();
        this.remainingPlaces = vo.getNumberOfNeed() -  vo.getNumberOfAttendees();
    }
}