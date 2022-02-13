package com.volunteer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.volunteer.entity.SignUpRecord;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.VolunteerActivity;
import com.volunteer.entity.VolunteerStatisticalInformation;
import com.volunteer.entity.vo.AuditeActivityVo;
import com.volunteer.mapper.SignUpRecordMapper;
import com.volunteer.mapper.VolunteerActivityMapper;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.mapper.VolunteerStatisticalInformationMapper;
import com.volunteer.service.VolunteerActivityService;
import com.volunteer.service.VolunteerStatisticalInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
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

    @Autowired
    private VolunteerActivityService volunteerActivityService;

    @Autowired
    private SignUpRecordMapper signUpRecordMapper;

    @Autowired
    private VolunteerActivityMapper volunteerActivityMapper;


    /**
     * 通过志愿者id查询志愿者统计活动信息
     */
    @Override
    public VolunteerStatisticalInformation selectVoluteerStaticalInformation(Integer volunteerId) {
        //判断传入volunteerId
        System.out.println(volunteerId);
        if (ObjectUtil.isNull(volunteerId)) {
            throw new RuntimeException("志愿者id为空，请输入");
        }
        //判断志愿者存在不存在
        Volunteer volunteer = volunteerMapper.selectById(volunteerId);
        if (ObjectUtil.isNull(volunteer)) {
            throw new RuntimeException("志愿者不存在");
        }
        VolunteerStatisticalInformation volunteerStatisticalInformation = volunteerStatisticalInformationMapper.selectByVolunteerId(volunteerId);
        //判断志愿者统计信息是否存在
        System.out.println(volunteerStatisticalInformation);
        if (ObjectUtil.isNull(volunteerStatisticalInformation)) {
            throw new RuntimeException("志愿者统计信息为空");
        }
        return volunteerStatisticalInformation;
    }

    /**
     * 活动结束后更新志愿者统计信息表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVoluteerStaticalInformation(AuditeActivityVo auditeActivity) {
        Boolean result = volunteerActivityService.isAuditedActivity(auditeActivity);
        if (!result) {
            //使用的志愿者报名记录
            Integer activityId = auditeActivity.getId();
            SignUpRecord signUpRecord = signUpRecordMapper.selectByVolunteerActivityID(activityId);
            //查询出活动信息
            VolunteerActivity volunteerActivity = volunteerActivityMapper.selectById(activityId);
            System.out.println(volunteerActivity);
            if (ObjectUtil.isNull(volunteerActivity)) {
                throw new RuntimeException("活动不存在");
            }
            //根据志愿者id查询志愿者统计信息
            VolunteerStatisticalInformation statisticalInfo = volunteerStatisticalInformationMapper.selectByVolunteerId(signUpRecord.getVolunteerId());
            System.out.println(statisticalInfo);
            //更新志愿者信息统计表
            statisticalInfo.setActivityNumbers(statisticalInfo.getActivityNumbers() + 1);
            statisticalInfo.setVolunteerDurations(statisticalInfo.getVolunteerDurations().add(BigDecimal.valueOf(volunteerActivity.getActualDuration())));
            if (ObjectUtil.isNotNull(statisticalInfo.getVolunteerPoints()) && ObjectUtil.isNotNull(volunteerActivity.getRewardPoints())) {
                statisticalInfo.setVolunteerPoints(statisticalInfo.getVolunteerPoints() + volunteerActivity.getRewardPoints());
            } else {
                throw new RuntimeException("积分为空");
            }
            volunteerStatisticalInformationMapper.updateById(statisticalInfo);

        }
    }

    @Override
    public IPage<VolunteerStatisticalInformation> getList(VolunteerStatisticalInformation params) {
        LambdaQueryWrapper<VolunteerStatisticalInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(params.getId()),VolunteerStatisticalInformation::getId,params.getId())
                .eq(Objects.nonNull(params.getVolunteerId()),VolunteerStatisticalInformation::getVolunteerId,params.getVolunteerId())
                .eq(VolunteerStatisticalInformation::getDeleted,0);
        Page<VolunteerStatisticalInformation> page = new Page<>(params.getPageNo(),params.getPageSize());
        return baseMapper.selectPage(page,queryWrapper);
    }

    /**
     * 更新志愿者统计信息
     *
     * @param volunteerId
     * @param activity
     */
    @Override
    public int update(Integer volunteerId, VolunteerActivity activity) {
        /// TODO 对于已结束未审核的活动（刚结束的活动），更新参加的志愿者的统计信息
        return 0;
    }
}
