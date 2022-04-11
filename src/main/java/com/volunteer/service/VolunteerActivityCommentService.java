package com.volunteer.service;

import com.volunteer.entity.VolunteerActivityComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.entity.vo.ActivityCommentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoyao
 * @since 2021-12-10
 */
public interface VolunteerActivityCommentService extends IService<VolunteerActivityComment> {
    /**
     * 增加评论
     * @param activityCommentVo
     * @return
     */
    void addComment(ActivityCommentVo activityCommentVo);

    /**
     * 删除评论
     * @param id
     */
    void deletedComment(Integer id);

    /**
     * 查询单条评论
     * @param id
     * @return
     */
    VolunteerActivityComment selectComment(Integer id);

    /**
     * 分页查询志愿者所有评论
     * @param volunteerId
     * @param pageNo 当前页
     * @param pageSize 页数据
     * @return
     */
    IPage<VolunteerActivityComment> selectVolunteerAllComment(Integer volunteerId,Integer pageNo,Integer pageSize);

    /**
     * 分页查询志愿者活动所有评论
     * @param volunteerActivityId
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<VolunteerActivityComment> selectActivityAllComment(Integer volunteerActivityId,Integer pageNo,Integer pageSize);
}
