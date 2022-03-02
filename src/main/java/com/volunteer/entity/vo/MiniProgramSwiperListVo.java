package com.volunteer.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: 梁峰源
 * @date: 2022/2/15 17:51
 * Description: 微信小程序轮播图url
 */
@Data
@Accessors(chain = true)//开启链式编程
@ApiModel(value = "微信小程序轮播图VoList",description = "微信小程序轮播图url集合")
public class MiniProgramSwiperListVo {

    @ApiModelProperty(name="swiperNum",value = "轮播数量图",required = true,example = "10")
    private Integer swiperNum;

    @ApiModelProperty(name="swiperList",value = "轮播图集合",required = true,example = "nothing")
    private List<MiniProgramSwiperVo> swiperList;
}
