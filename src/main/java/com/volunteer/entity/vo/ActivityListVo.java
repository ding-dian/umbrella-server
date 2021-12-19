package com.volunteer.entity.vo;

import com.volunteer.entity.VolunteerActivity;
import lombok.Data;

import java.util.List;

/**
 * @author: 梁峰源
 * @date: 2021/12/19 12:15
 * Description:
 */
@Data
public class ActivityListVo {
    /*
     *需要的字段
     *  1. list
     *  2. total 活动总数
     */
    /**
     * 活动列表
     */
    private List<VolunteerActivity> list;

    /**
     * 活动的总数
     */
    private Long total;
}
