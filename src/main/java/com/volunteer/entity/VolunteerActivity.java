package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    private LocalDateTime endTime;

    /**
     * 状态【00:进行中，01:未开始，02:已结束】
     */
    private String status;

    /**
     * 逻辑删除【0:未删除，1:已删除】
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

    /**
     * 更新人
     */
    private String updateBy;


}
