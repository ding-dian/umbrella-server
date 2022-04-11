package com.volunteer.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @author: 梁峰源
 * @date: 2021/12/19 12:15
 * Description:
 */
@Data
@Getter
@ApiModel(value = "活动列表对象",description = "将ActivityListItemVo活动查询到的集合包装" )
public class ActivityListVo {
    /*
     *需要的字段
     *  1. list
     *  2. total 活动总数
     */
    /**
     * 活动列表
     */
    @ApiModelProperty(name = "List<ActivityListItemVo>",value = "活动列表",required = true)
    private List<ActivityListItemVo> list;

    /**
     * 活动的总数
     */
    @ApiModelProperty(name = "total",value = "分页返回的活动条数",required = true)
    private Long total;
}
