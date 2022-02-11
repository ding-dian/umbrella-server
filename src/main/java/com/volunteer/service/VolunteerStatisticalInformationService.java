package com.volunteer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.volunteer.entity.VolunteerStatisticalInformation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.entity.vo.AuditeActivityVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoyao
 * @since 2021-11-25
 */
public interface VolunteerStatisticalInformationService extends IService<VolunteerStatisticalInformation> {
    /**
     * 通过志愿者id查询志愿者统计活动信息
     * @param volunteerId
     * @return
     */
    VolunteerStatisticalInformation selectVoluteerStaticalInformation(Integer volunteerId);

    /**
     * 活动结束后更新志愿者统计信息表
     * @param auditeActivity
     * @return
     */
    void updateVoluteerStaticalInformation(AuditeActivityVo auditeActivity);

    /**
     * 列表查询
     * @return
     */
    IPage<VolunteerStatisticalInformation> getList(VolunteerStatisticalInformation params);
}
