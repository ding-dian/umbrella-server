package com.volunteer.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.component.RedisOperator;
import com.volunteer.entity.SignUpRecord;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.VolunteerActivity;
import com.volunteer.entity.common.ActivityStatus;
import com.volunteer.entity.common.SignUpStatus;
import com.volunteer.entity.vo.SignUpVo;
import com.volunteer.mapper.SignUpRecordMapper;
import com.volunteer.mapper.VolunteerActivityMapper;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.service.SignUpRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
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

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 报名
     * @param query
     * @return  0失败，1成功，2已报名，3未登录
     */
    @Override
    public int signUp(SignUpVo query) {
        // 根据token从redis中获取用户信息
        String jsonStr = redisOperator.get(query.getToken());
        if (StringUtils.isEmpty(jsonStr)) {
            // 用户未登录
            return SignUpStatus.NOT_LOGIN;
        }
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        if (ObjectUtil.isNotNull(jsonObject)) {
            Volunteer volunteer = JSONUtil.toBean(jsonObject,Volunteer.class);
            VolunteerActivity activity = volunteerActivityMapper.selectById(query.getActivityId());
            // 志愿者与活动都存在
            if (ObjectUtil.isNotNull(volunteer) && ObjectUtil.isNotNull(activity)) {
                // 判断是否已经报名过了
                LambdaQueryWrapper<SignUpRecord> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SignUpRecord::getVolunteerId,volunteer.getId()).eq(SignUpRecord::getVolunteerActivityId,activity.getId());
                SignUpRecord signUpRecord = baseMapper.selectOne(queryWrapper);
                if (ObjectUtil.isNotNull(signUpRecord)) {
                    // 已经报名过
                    if (signUpRecord.getDeleted() == 0) {
                        return SignUpStatus.ALREADY_SIGNED_UP;
                    }
                    // 之前取消了报名，现在又重新报名了
                    signUpRecord.setDeleted(0);
                    return baseMapper.updateById(signUpRecord);
                }
                // 报名
                signUpRecord = new SignUpRecord();
                signUpRecord.setVolunteerId(volunteer.getId()).setVolunteerActivityId(activity.getId()).setCreateAt(LocalDateTime.now()).setDeleted(0);
                int result = baseMapper.insert(signUpRecord);
                return result;
            }
        }
        return SignUpStatus.SIGN_UP_FAIL;
    }

    /**
     * @param query
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelRegistration(SignUpVo query) {
        // 根据token从redis中获取用户信息
        String jsonStr = redisOperator.get(query.getToken());
        if (StringUtils.isEmpty(jsonStr) || ObjectUtil.isNull(query.getActivityId())) {
            return;
        }
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        Volunteer volunteer = JSONUtil.toBean(jsonObject, Volunteer.class);

        LambdaQueryWrapper<SignUpRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SignUpRecord::getVolunteerId,volunteer.getId()).eq(SignUpRecord::getVolunteerActivityId,query.getActivityId());
        SignUpRecord signUpRecord = baseMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNotNull(signUpRecord)) {
            // 逻辑删除
            signUpRecord.setDeleted(1);
            baseMapper.updateById(signUpRecord);
        }
    }

    @Override
    public boolean checkSignUpState(SignUpVo query) {
        Volunteer volunteer = redisOperator.getObjectByToken(query.getToken(), Volunteer.class);
        if (ObjectUtil.isNotNull(volunteer)) {
            LambdaQueryWrapper<SignUpRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SignUpRecord::getVolunteerId,volunteer.getId())
                    .eq(SignUpRecord::getVolunteerActivityId,query.getActivityId());
            return baseMapper.selectCount(queryWrapper) > 0;
        } else {
            throw new RuntimeException("请重新登陆后重试");
        }
    }
}
