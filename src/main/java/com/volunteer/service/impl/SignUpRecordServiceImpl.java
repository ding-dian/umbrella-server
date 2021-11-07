package com.volunteer.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.entity.SignUpRecord;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.VolunteerActivity;
import com.volunteer.entity.common.ActivityStatus;
import com.volunteer.entity.vo.SignUpVo;
import com.volunteer.mapper.SignUpRecordMapper;
import com.volunteer.mapper.VolunteerActivityMapper;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.service.SignUpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Service
public class SignUpRecordServiceImpl extends ServiceImpl<SignUpRecordMapper, SignUpRecord> implements SignUpRecordService {

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Autowired
    private VolunteerActivityMapper volunteerActivityMapper;

    @Override
    public int signUp(SignUpVo query) {
        VolunteerActivity activity = volunteerActivityMapper.selectById(query.getActivityId());
        Volunteer volunteer = volunteerMapper.selectById(query.getVolunteerId());
        // 如果志愿者或者活动都存在
        if (ObjectUtil.isNotNull(volunteer) && ObjectUtil.isNotNull(volunteer)) {
            // 检查活动是否未开始，如果已经开始或者已经结束则无法报名
            if (activity.getStatus().equals(ActivityStatus.NOT_STARTED)) {
                // 检查是否已经报名，如果已经报名则立刻结束，如果未报名就新增一条报名记录
                LambdaQueryWrapper<SignUpRecord> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SignUpRecord::getVolunteerId, query.getVolunteerId()).eq(SignUpRecord::getVolunteerActivityId,query.getActivityId());
                List<SignUpRecord> signUpRecords = baseMapper.selectList(queryWrapper);
                // 如果之前没报名
                if (CollectionUtil.isEmpty(signUpRecords)) {
                    SignUpRecord signUpRecord = new SignUpRecord();
                    signUpRecord.setVolunteerId(query.getVolunteerId()).setVolunteerActivityId(query.getActivityId());
                    return baseMapper.insert(signUpRecord);
                }
            }
        }
        return 0;
    }
}
