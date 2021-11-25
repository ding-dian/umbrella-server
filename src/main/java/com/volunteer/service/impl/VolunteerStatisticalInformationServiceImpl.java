package com.volunteer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.VolunteerStatisticalInformation;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.mapper.VolunteerStatisticalInformationMapper;
import com.volunteer.service.VolunteerStatisticalInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoyao
 * @since 2021-11-25
 */
@Service
public class VolunteerStatisticalInformationServiceImpl extends ServiceImpl<VolunteerStatisticalInformationMapper, VolunteerStatisticalInformation> implements VolunteerStatisticalInformationService {
    @Autowired
    private VolunteerStatisticalInformationMapper volunteerStatisticalInformationMapper;

    @Autowired
    private VolunteerMapper volunteerMapper;
    /**
     * 通过志愿者id查询志愿者统计活动信息
     *
     * @param volunteerId
     * @return
     */
    @Override
    public VolunteerStatisticalInformation selectVoluteerStaticalInformation(Integer volunteerId) {
        //判断传入volunteerId
        System.out.println(volunteerId);
        if (ObjectUtil.isNull(volunteerId)){
            throw new RuntimeException("志愿者id为空，请输入");
        }
        //判断志愿者存在不存在
        Volunteer volunteer = volunteerMapper.selectById(volunteerId);
        if(ObjectUtil.isNull(volunteer)){
            throw new RuntimeException("志愿者不存在");
        }
        VolunteerStatisticalInformation volunteerStatisticalInformation = volunteerStatisticalInformationMapper.selectByVolunteerId(volunteerId);
        //判断志愿者统计信息是否存在
        System.out.println(volunteerStatisticalInformation);
        if (ObjectUtil.isNull(volunteerStatisticalInformation)){
            throw new RuntimeException("志愿者统计信息为空");
        }
        return volunteerStatisticalInformation;
    }
}
