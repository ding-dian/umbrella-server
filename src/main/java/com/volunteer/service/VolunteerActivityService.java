package com.volunteer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.entity.VolunteerActivity;
import com.volunteer.entity.vo.ActivityListVo;
import com.volunteer.entity.vo.AuditeActivityVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
public interface VolunteerActivityService extends IService<VolunteerActivity> {
    /**
     * 创建一个志愿者活动
     * @param volunteerActivity
     * @return
     */
    int createActivity(VolunteerActivity volunteerActivity) throws Exception;

    /**
     * 删除一个志愿者活动
     * @param id
     * @return
     */
    int deleteActivity(Integer id);

    /**
     * 批量删除志愿者活动
     * @param ids
     */
     void deleteListActivity(Integer[] ids);

    /**
     * 活动结束更新志愿者活动真实时长和状态接口
     * @param auditeActivity
     * @return
     */
    VolunteerActivity updateActivityStatus(AuditeActivityVo auditeActivity);

    /**
     * 审核志愿者活动接口
     *
     * @param auditeActivity
     * @return
     */
    Boolean isAuditedActivity(AuditeActivityVo auditeActivity);

    /**
     * 查询单个活动
     *
     * @param id
     * @return
     */
    VolunteerActivity selectOne(Integer id);

    /**
     * 更新志愿者活动信息
     * @param volunteerActivity
     * @return
     */
    int updateActivity(VolunteerActivity volunteerActivity);

    /**
     * 根据条件查询志愿者列表
     *
     * @param volunteerActivity
     * @return
     */
    IPage<VolunteerActivity> selectListActivity(VolunteerActivity volunteerActivity);

    /**
     * 根据活动状态查询志愿活动列表
     * @param stutas
     * @return
     */
    ActivityListVo findListByStutas(String stutas , Integer pageNo, Integer pageSize);
}
