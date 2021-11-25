package com.volunteer.service;

import com.volunteer.entity.VolunteerActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.entity.vo.AuditeActivityVo;

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
     * 审核志愿者活动接口
     * @param auditeActivity
     * @return
     */
    VolunteerActivity isAuditedActivity(AuditeActivityVo auditeActivity);

}
