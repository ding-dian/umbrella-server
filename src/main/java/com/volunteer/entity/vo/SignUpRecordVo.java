package com.volunteer.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 报名列表VO对象
 */
@Data
@Accessors(chain = true)
public class SignUpRecordVo {

    private Integer id;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 志愿者名称
     */
    private String volunteerName;

    /**
     * 报名时间
     */
    private String createAt;

    /**
     * 活动状态
     */
    private String status;

    /**
     * 页大小
     */
    private long pageSize;

    /**
     * 页号
     */
    private long pageNo;
}
