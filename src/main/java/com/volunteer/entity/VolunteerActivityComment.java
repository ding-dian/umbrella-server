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
 * @author xiaoyao
 * @since 2021-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VolunteerActivityComment implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 志愿者id
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

    /**
     * 【0】未删除 【1】删除
     */
    private Integer deleted;

    /**
     * 评论时间
     */
    private LocalDateTime commentTime;

    /**
     * 点赞数量
     */
    private Integer thumbUpNumber;


}
