package com.volunteer.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.component.RedisOperator;
import com.volunteer.entity.SignUpRecord;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.VolunteerActivity;
import com.volunteer.entity.common.SignUpStatus;
import com.volunteer.entity.vo.SignUpListVo;
import com.volunteer.entity.vo.SignUpRecordVo;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
     * @return  0失败，1成功，2已报名，3未登录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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
                    // 更新报名人数
                    activity.setNumberOfAttendees(activity.getNumberOfAttendees() + 1);
                    volunteerActivityMapper.updateById(activity);
                    return baseMapper.updateById(signUpRecord);
                }
                // 报名
                signUpRecord = new SignUpRecord();
                signUpRecord.setVolunteerId(volunteer.getId()).setVolunteerActivityId(activity.getId()).setCreateAt(LocalDateTime.now()).setDeleted(0).setIsSignIn(0);
                // 更新报名人数
                activity.setNumberOfAttendees(activity.getNumberOfAttendees() + 1);
                volunteerActivityMapper.updateById(activity);
                return baseMapper.insert(signUpRecord);
            }
        }
        return SignUpStatus.SIGN_UP_FAIL;
    }

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
            return checkForRegistration(volunteer.getId(), query.getActivityId());
        } else {
            throw new RuntimeException("请重新登陆后重试");
        }
    }

    @Override
    public List<SignUpListVo> getSignUpListByActivityId(Integer activityId) {
        if (Objects.nonNull(activityId)) {
            LambdaQueryWrapper<SignUpRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SignUpRecord::getVolunteerActivityId,activityId);
            List<SignUpRecord> signUpRecords = baseMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(signUpRecords)) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                List<SignUpListVo> result = new ArrayList<>();
                signUpRecords.forEach((record) -> {
                    Volunteer volunteer = volunteerMapper.selectById(record.getVolunteerId());
                    if (Objects.nonNull(volunteer)) {
                        SignUpListVo vo = new SignUpListVo();
                        LocalDateTime createAt = record.getCreateAt();
                        if (Objects.nonNull(createAt)) {
                            vo.setDate(createAt.format(dateTimeFormatter));
                        }
                        vo.setAvatar(volunteer.getAvatarUrl());
                        vo.setId(volunteer.getId());
                        vo.setIsSignIn(record.getIsSignIn());
                        result.add(vo);
                    }
                });
                return result;
            }
        }
        return Collections.emptyList();
    }

    /**
     * 根据ID删除报名信息
     *
     * @param id
     */
    @Override
    public void deleteRecordById(Integer id) {
        // 更新活动的报名人数
        VolunteerActivity activity = volunteerActivityMapper.selectById(baseMapper.selectById(id).getVolunteerActivityId());
        activity.setNumberOfAttendees(activity.getNumberOfAttendees() - 1);
        baseMapper.deleteById(id);
    }

    /**
     * 分页查询报名列表
     */
    @Override
    public Page<SignUpRecordVo> getList(SignUpRecordVo signUpRecordVo) {
        LambdaQueryWrapper<SignUpRecord> queryWrapper = new LambdaQueryWrapper<>();
        Volunteer volunteer;
        VolunteerActivity volunteerActivity;
        if (StringUtils.isNotEmpty(signUpRecordVo.getVolunteerName())) {
            // 根据姓名查询
            List<Volunteer> volunteers = volunteerMapper.selectList(new LambdaQueryWrapper<Volunteer>().eq(Volunteer::getName, signUpRecordVo.getVolunteerName()));
            if (CollectionUtil.isNotEmpty(volunteers)) {
                queryWrapper.in(SignUpRecord::getVolunteerId, volunteers.stream().map(Volunteer::getId).collect(Collectors.toList()));
            }
        }
        if (StringUtils.isNotEmpty(signUpRecordVo.getActivityName())) {
            // 根据活动名查询
            List<VolunteerActivity> activities = volunteerActivityMapper.selectList(new LambdaQueryWrapper<VolunteerActivity>().eq(VolunteerActivity::getActivityName, signUpRecordVo.getActivityName()));
            if (CollectionUtil.isNotEmpty(activities)) {
                queryWrapper.in(SignUpRecord::getVolunteerActivityId, activities.stream().map(VolunteerActivity::getId).collect(Collectors.toList()));
            }
        }
        Page<SignUpRecord> page = new Page<>(signUpRecordVo.getPageNo(), signUpRecordVo.getPageSize());
        IPage<SignUpRecord> selectPage = baseMapper.selectPage(page, queryWrapper);
        List<SignUpRecordVo> resultList = new LinkedList<>();
        for (SignUpRecord item : selectPage.getRecords()) {
            SignUpRecordVo vo = new SignUpRecordVo();
            volunteer = volunteerMapper.selectById(item.getVolunteerId());
            if (Objects.nonNull(volunteer)) {
                vo.setVolunteerName(volunteer.getName());
            }
            volunteerActivity = volunteerActivityMapper.selectById(item.getVolunteerActivityId());
            if (Objects.nonNull(volunteerActivity)) {
                vo.setActivityName(volunteerActivity.getActivityName()).setStatus(volunteerActivity.getStatus());
            }
            if (Objects.nonNull(item.getCreateAt())) {
                vo.setCreateAt(item.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            vo.setId(item.getId());
            resultList.add(vo);
        }
        Page<SignUpRecordVo> result = new Page<>(selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal());
        result.setRecords(resultList);
        return result;
    }


    public boolean checkForRegistration(Integer volunteerId,Integer activityId) {
        return Objects.nonNull(getRecord(volunteerId,activityId));
    }

    public SignUpRecord getRecord(Integer volunteerId,Integer activityId) {
        LambdaQueryWrapper<SignUpRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SignUpRecord::getVolunteerId,volunteerId)
                .eq(SignUpRecord::getVolunteerActivityId,activityId);
        return baseMapper.selectOne(queryWrapper);
    }
}
