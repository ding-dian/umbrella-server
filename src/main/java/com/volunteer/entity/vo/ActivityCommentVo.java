package com.volunteer.entity.vo;

import lombok.Data;

/**
 * @author 何福任
 */
@Data
public class ActivityCommentVo {
    /**
     *志愿者id
     */
    private Integer volunteerId;

    /**
     * 志愿者活动id
     */
    private Integer volunteerActivityId;

    /**
     * 评论内容
     */
    private String content;

}
