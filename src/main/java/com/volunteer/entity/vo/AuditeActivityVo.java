package com.volunteer.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 逍遥
 */
@ApiModel(value = "审核",description = "志愿活动结束更新真实时长")
@Data
public class AuditeActivityVo {
    /**
     * 活动id
     */
    @ApiModelProperty(required = true,example = "1")
    private Integer id;

    /**
     * 真实时长
     */
    @ApiModelProperty(required = true,example = "4.0")
    private Double actualDuration;
}
