package com.volunteer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.VolunteerActivity;
import com.volunteer.entity.VolunteerActivityComment;
import com.volunteer.entity.vo.ActivityCommentVo;
import com.volunteer.mapper.VolunteerActivityCommentMapper;
import com.volunteer.mapper.VolunteerActivityMapper;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.service.VolunteerActivityCommentService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaoyao
 * @since 2021-12-10
 */
@Slf4j
@Service
public class VolunteerActivityCommentServiceImpl extends ServiceImpl<VolunteerActivityCommentMapper, VolunteerActivityComment> implements VolunteerActivityCommentService {
    @Autowired
    VolunteerActivityCommentMapper volunteerActivityCommentMapper;
    @Autowired
    VolunteerMapper volunteerMapper;
    @Autowired
    VolunteerActivityMapper volunteerActivityMapper;

    /**
     * 增加评论
     *
     * @param activityCommentVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(ActivityCommentVo activityCommentVo) {
        //参数判断 
        if (ObjectUtil.isNull(activityCommentVo)) {
            throw new RuntimeException("传入信息为空");
        }
        //检查志愿者
        int volunteerId = activityCommentVo.getVolunteerId();
        checkId(volunteerId);
        //查找志愿者是否存在
        Volunteer volunteer = volunteerMapper.selectById(volunteerId);
        if (ObjectUtil.isNull(volunteer)) {
            throw new RuntimeException("志愿者不存在");
        }
        checkObject(volunteer.getDeleted());
        //检查志愿者活动 
        int volunteerActivityId = activityCommentVo.getVolunteerActivityId();
        checkId(volunteerActivityId);
        //查找志愿者是否存在
        VolunteerActivity volunteerActivity = volunteerActivityMapper.selectById(volunteerActivityId);
        if (ObjectUtil.isNull(volunteerActivityMapper)) {
            throw new RuntimeException("志愿者活动不存在");
        }
        //检查志愿者活动
        checkObject(volunteerActivity.getDeleted());

        //给VolunteerActivityComment属性赋值
        VolunteerActivityComment volunteerActivityComment = new VolunteerActivityComment();
        volunteerActivityComment
                .setVolunteerId(volunteerId)
                .setVolunteerActivityId(volunteerActivityId)
                .setContent(activityCommentVo.getContent())
                .setCommentTime(LocalDateTime.now());
        //insert 数据库
        volunteerActivityCommentMapper.insert(volunteerActivityComment);
        //根据志愿者活动和志愿者id查找数据库 返回对象

    }

    /**
     * 删除评论
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletedComment(Integer id) {
        //检查
        VolunteerActivityComment volunteerActivityComment = checkIdAndComment(id);
        //逻辑删除
        volunteerActivityComment.setDeleted(1);
        volunteerActivityCommentMapper.updateById(volunteerActivityComment);
    }

    /**
     * 查询单条评论
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public VolunteerActivityComment selectComment(Integer id) {
        VolunteerActivityComment volunteerActivityComment = checkIdAndComment(id);
        return volunteerActivityComment;
    }

    /**
     * @param volunteerId
     * @param pageNo      当前页
     * @param pageSize    页数据
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<VolunteerActivityComment> selectVolunteerAllComment(Integer volunteerId, Integer pageNo, Integer pageSize) {
        //检查志愿者id
        checkId(volunteerId);
        Volunteer volunteer = volunteerMapper.selectById(volunteerId);
        if (ObjectUtil.isNull(volunteer)) {
            throw new RuntimeException("志愿者不存在");
        }
        //检查志愿者是否被删除
        checkObject(volunteer.getDeleted());
        LambdaQueryWrapper<VolunteerActivityComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(volunteerId), VolunteerActivityComment::getVolunteerId, volunteerId);
        Page<VolunteerActivityComment> page = new Page<>();
        page.setCurrent(pageNo).setSize(pageSize);
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 分页查询志愿者活动所有评论信息
     * @param volunteerActivityId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<VolunteerActivityComment> selectActivityAllComment(Integer volunteerActivityId,Integer pageNo,Integer pageSize) {
        checkId(volunteerActivityId);
        VolunteerActivity volunteerActivity = volunteerActivityMapper.selectById(volunteerActivityId);
        if (ObjectUtil.isNull(volunteerActivity)) {
            throw new RuntimeException("志愿者活动不存在");
        }
        //检查志愿者是否被删除
        checkObject(volunteerActivity.getDeleted());
        LambdaQueryWrapper<VolunteerActivityComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(volunteerActivityId), VolunteerActivityComment::getVolunteerId, volunteerActivityId);
        Page<VolunteerActivityComment> page = new Page<>();
        page.setCurrent(pageNo).setSize(pageSize);
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 检查id合法性
     * 检测查询出来的结果是否存在
     * 结果的deleted==1 已删除 不存在
     *
     * @param id
     * @return 活动评论信息
     */
    private VolunteerActivityComment checkIdAndComment(Integer id) {
        if (id == null || id < 0) {
            throw new RuntimeException("请输入合法id");
        }
        VolunteerActivityComment volunteerActivityComment
                = volunteerActivityCommentMapper.selectById(id);
        if (volunteerActivityComment == null || volunteerActivityComment.getDeleted() == 1) {
            throw new RuntimeException("该id的评论不存在");
        }
        return volunteerActivityComment;
    }

    /**
     * 检查id
     *
     * @param id
     * @return
     */
    private boolean checkId(Integer id) {
        if (id == null || id < 0) {
            throw new RuntimeException("请输入正确id");
        }
        return true;
    }

    /**
     * 检查模型对象
     *
     * @param deleted
     * @return
     */
    private boolean checkObject(Integer deleted) {
        if (deleted == 1) {
            throw new RuntimeException("对象已被删除");
        }
        return true;
    }
}
