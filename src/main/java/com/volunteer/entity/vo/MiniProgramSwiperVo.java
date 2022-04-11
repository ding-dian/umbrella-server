package com.volunteer.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: 梁峰源
 * @date: 2022/2/15 15:00
 * Description: 微信小程序轮播图
 */
@ApiModel(value = "微信小程序轮播图Vo",description = "志愿活动结束更新真实时长")
@Data
@Accessors(chain = true)//开启链式编程
public class MiniProgramSwiperVo {

    @ApiModelProperty(name="uid",value = "图片id，element中规定要用uid表示",required = true,example = "1")
    private Integer uid;

    @ApiModelProperty(name="title",value = "图片标题",required = true,example = "图书馆整理活动")
    private String title;

    @ApiModelProperty(name="summary",value = "图片描述",required = true,example = "活动心得...")
    private String summary;

    @ApiModelProperty(name="storePath",value = "图片在oss中的路径",required = true,example = "qxImages/categoryImages0")
    private String storePath;

    @ApiModelProperty(name="url",value = "爱心雨伞图片的url",required = true,example = "http://xxxx.png")
    private String url;//图片url地址
}