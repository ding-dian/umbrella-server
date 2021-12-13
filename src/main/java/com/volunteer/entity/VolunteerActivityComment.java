package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-12-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "活动评论对象",description = "用于接收活动评论的实体")
public class VolunteerActivityComment implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * 志愿者id
     */
    @ApiModelProperty(hidden = true)
    private Integer volunteerId;

    /**
     * 志愿者活动id
     */
    @ApiModelProperty(hidden = true)
    private Integer volunteerActivityId;

    /**
     * 评论内容
     */
    @ApiModelProperty(hidden = true)
    private String content;

    /**
     * 【0】未删除 【1】删除
     */
    @ApiModelProperty(hidden = true)
    private Integer deleted;

    /**
     * 评论时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime commentTime;

    /**
     * 点赞数量
     */
    @ApiModelProperty(hidden = true)
    private Integer thumbUpNumber;


}
