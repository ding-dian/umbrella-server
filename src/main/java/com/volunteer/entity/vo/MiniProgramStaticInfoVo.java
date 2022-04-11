package com.volunteer.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: 梁峰源
 * @date: 2022/3/1 17:14
 * Description: 微信小程序用户主界面静态信息封装
 */
@Data
@Accessors(chain = true)//开启链式编程
@ApiModel(value = "微信小程序用户主页静态信息",description = "用来展示微信小程序用户主页的静态信息")
public class MiniProgramStaticInfoVo {
    @ApiModelProperty(name="adminPhone",value = "管理员电话",required = true,example = "176xxxx9756")
    private String adminPhone;

    @ApiModelProperty(name="swiperVo",value = "微信小程序关于我们界面的内容",required = true)
    private MiniProgramSwiperVo swiperVo;
}
