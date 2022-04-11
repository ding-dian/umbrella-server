package com.volunteer.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 何福任
 */
@ApiModel(value ="活动评论vo",description = "用于接收活动评论的实体")
@Data
public class ActivityCommentVo {
    /**
     *志愿者id
     */
    @ApiModelProperty(required = true,example = "1")
    private Integer volunteerId;

    /**
     * 志愿者活动id
     */
    @ApiModelProperty(required = true,example = "1")
    private Integer volunteerActivityId;

    /**
     * 评论内容
     */
    @ApiModelProperty(required = true,example = "这是一次有意思的活动")
    private String content;

}
