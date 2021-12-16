package com.volunteer.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author VernHe
 * @date 2021年11月07日 20:39
 * @Description 报名参加活动时接收参数
 */
@ApiModel(value = "对象", description = "志愿者报名参加活动")
@Data
@Accessors(chain = true)
public class SignUpVo {
    /**
     * 报名的志愿者ID
     */
    @ApiModelProperty(required = true, example = "1")
    private Integer volunteerId;

    /**
     * 所报名的活动的ID
     */
    @ApiModelProperty(required = true, example = "1")
    private Integer activityId;
}
