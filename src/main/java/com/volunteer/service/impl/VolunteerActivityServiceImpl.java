package com.volunteer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.volunteer.entity.VolunteerActivity;
import com.volunteer.entity.vo.AuditeActivityVo;
import com.volunteer.mapper.VolunteerActivityMapper;
import com.volunteer.service.VolunteerActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Service
public class VolunteerActivityServiceImpl extends ServiceImpl<VolunteerActivityMapper, VolunteerActivity> implements VolunteerActivityService {

    /**
     * 创建一个志愿者活动
     *
     * @param volunteerActivity
     * @return 1为成功创建
     */

    @Override
    public int createActivity(VolunteerActivity volunteerActivity) throws Exception {
        System.out.println(volunteerActivity);

        //如果志愿者活动不为空
        if (volunteerActivity != null) {
            //判断活动必须项是否为空
            if (StrUtil.isEmpty(volunteerActivity.getActivityName())) {
                throw new RuntimeException("志愿者活动名称不能为空");
            }
            if (StrUtil.isEmpty(volunteerActivity.getActivityAddress())) {
                throw new RuntimeException("志愿者地址不能为空");
            }
            if (ObjectUtil.isNull(volunteerActivity.getNumberOfNeed())) {
                throw new RuntimeException("志愿者所需人数不能为空");
            }
            if (StrUtil.isEmpty(volunteerActivity.getCreateBy())) {
                throw new RuntimeException("志愿者创建人不能为空");
            }
            if (volunteerActivity.getEndTime() == null) {
                throw new RuntimeException("志愿者活动结束时间不能为空");
            }
            if (volunteerActivity.getStartTime() == null) {
                throw new RuntimeException("志愿者活动开始时间不能为空");
            }
            if (volunteerActivity.getEndTime().isBefore(volunteerActivity.getStartTime())) {
                throw new RuntimeException("志愿者活动结束时间不能早于开始时间");
            }
            if (volunteerActivity.getStartTime().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("志愿者活动开始时间不能早于当前时间");
            }
            if (volunteerActivity.getPredictDuration() < 0 || volunteerActivity.getPredictDuration() > 12) {
                throw new RuntimeException("单次志愿活动最多可累计十二小时，且不能小于0小时");
            }

            /**
             * 设置活动状态和奖励积分
             */
            volunteerActivity.setRewardPoints(5);
            volunteerActivity.setStatus("01");
            volunteerActivity.setCreateAt(LocalDateTime.now());
            baseMapper.insert(volunteerActivity);
            return 1;
        } else {
            return -1;
        }

    }

    /**
     * 删除一个志愿者活动
     *
     * @param id
     * @return
     */
    @Override
    public int deleteActivity(Integer id) {
        if (id == null) {
            throw new RuntimeException("id为空");
        }
        VolunteerActivity volunteerActivity = baseMapper.selectById(id);
        //根据id查询志愿者活动是否存在，存在才做逻辑删除 将delete设置为 1
        if (ObjectUtil.isNotNull(volunteerActivity)) {
            volunteerActivity.setDeleted(1);
            return baseMapper.updateById(volunteerActivity);
        }
        return 0;
    }

    /**
     * 批量删除志愿者活动
     *
     * @param ids
     */
    @Override
    public void deleteListActivity(Integer[] ids) {
        for (int i = 0; i < ids.length; i++) {
            deleteActivity(ids[i]);
        }
    }

    /**
     * 审核志愿者活动接口
     *
     * @param auditeActivity
     * @return
     */
    @Override
    public VolunteerActivity isAuditedActivity(AuditeActivityVo auditeActivity) {
        //判断是否传入参数
        if (ObjectUtil.isNull(auditeActivity)) {
            throw new RuntimeException("请输入活动id和真实志愿活动时长");
        }
        //判断传入id是否和法
        if (ObjectUtil.isNull(auditeActivity.getId()) || auditeActivity.getId() < 0) {
            throw new RuntimeException("id为空或id<0");
        }
        //判断志愿时长是否正常
        if (auditeActivity.getActualDuration() < 0 ) {
            throw new RuntimeException("单次志愿活动时长不能小于零");
        }
        //判断志愿活动是否存在
        VolunteerActivity volunteerActivity = baseMapper.selectById(auditeActivity.getId());
        if (ObjectUtil.isNull(volunteerActivity)) {
            throw new RuntimeException("志愿者活动不存在");
        }
        System.out.println(volunteerActivity);
        //判断活动是否结束 如果活动结束时间大于当前时间则已结束
        if (volunteerActivity.getEndTime().isBefore(LocalDateTime.now())) {
            volunteerActivity.setStatus("02");
        }else {
            throw new RuntimeException("活动还未结束，还无法进行审核");
        }
        //判断活动是否已经被审核
        if (volunteerActivity.getIsAudited() == 1) {
            throw new RuntimeException("该志愿活动已经被审核");
        }
        volunteerActivity.setActualDuration(auditeActivity.getActualDuration());
        volunteerActivity.setIsAudited(1);
        baseMapper.updateById(volunteerActivity);

        return volunteerActivity;
    }
}
