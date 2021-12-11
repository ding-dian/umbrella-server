package com.volunteer.service;

import com.volunteer.entity.VolunteerActivityComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.entity.vo.ActivityCommentVo;

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


}
