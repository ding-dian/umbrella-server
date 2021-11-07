package com.volunteer.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author VernHe
 * @date 2021年11月07日 20:39
 * @Description 报名参加活动时接收参数
 */
@Data
@Accessors(chain = true)
public class SignUpVo {
    /**
     * 报名的志愿者ID
     */
    private Integer volunteerId;

    /**
     * 所报名的活动的ID
     */
    private Integer activityId;
}
