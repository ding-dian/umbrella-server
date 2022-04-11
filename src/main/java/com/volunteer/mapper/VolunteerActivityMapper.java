package com.volunteer.mapper;

import com.volunteer.entity.Volunteer;
import com.volunteer.entity.VolunteerActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
public interface VolunteerActivityMapper extends BaseMapper<VolunteerActivity> {
    /**
     * 批量更新活动状态
     * @param list
     * @return
     */
    int updateStatus(List<VolunteerActivity> list);

    /**
     * 根据活动名称查询
     * @param name
     * @return
     */
    VolunteerActivity selectByName(String name);

    /**
     * 查询一个用户参加的所有活动
     * @param volunteerID 志愿者的id
     * @return 返回查询到的集合
     */
    List<VolunteerActivity> selectUserActivityList(Integer volunteerID,Integer skip, Integer pageSize);
}
