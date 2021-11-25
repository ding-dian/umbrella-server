package com.volunteer.service;

import com.volunteer.entity.VolunteerStatisticalInformation;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
