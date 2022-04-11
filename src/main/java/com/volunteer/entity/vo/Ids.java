package com.volunteer.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author VernHe
 * @date 2021年12月13日 0:10
 */
@ApiModel(value = "数组",description = "用来接收id")
@Data
public class Ids {

    @ApiModelProperty(required = true,example = "[10,11,12]")
    Integer[] ids;
}
