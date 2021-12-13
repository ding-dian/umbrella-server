package com.volunteer.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.volunteer.entity.VolunteerActivityComment;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.vo.ActivityCommentVo;
import com.volunteer.service.VolunteerActivityCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaoyao
 * @since 2021-12-10
 */

@Api(tags = "活动评论模块")
@RestController
@RequestMapping("/volunteerActivityComment")
public class VolunteerActivityCommentController {
    @Autowired
    VolunteerActivityCommentService volunteerActivityCommentService;

    @ApiOperation("添加接口")
    @PostMapping("/addComment")
    public Result addComment(@RequestBody ActivityCommentVo activityCommentVo) {
        try {
            volunteerActivityCommentService.addComment(activityCommentVo);
            return ResultGenerator.getSuccessResult("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }

    @ApiOperation("删除接口")
    @PostMapping("/deletedComment")
    @ResponseBody
    public Result deletedComment(Integer id) {
        try {
            volunteerActivityCommentService.deletedComment(id);
            return ResultGenerator.getSuccessResult("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }
    @ApiOperation("查询接口")
    @GetMapping("/findOneComment")
    @ResponseBody
    public Result findOneComment(Integer id) {
        try {
            VolunteerActivityComment volunteerActivityComment = volunteerActivityCommentService.selectComment(id);
            return ResultGenerator.getSuccessResult(volunteerActivityComment);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }
    @ApiOperation("查询接口")
    @GetMapping("/selectVolunteerAllComment")
    @ResponseBody
    public  Result selectVolunteerAllComment(Integer volunteerId,
                                             @RequestParam(defaultValue = "1") Integer pageNo,
                                             @RequestParam(defaultValue = "20") Integer pageSize){
        try {
            IPage<VolunteerActivityComment> page =
                    volunteerActivityCommentService.selectVolunteerAllComment(volunteerId, pageNo, pageSize);
            return ResultGenerator.getSuccessResult(page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }
    @ApiOperation("分页查询接口")
    @GetMapping("/selectActivityAllComment")
    @ResponseBody
    public  Result selectActivityAllComment(Integer volunteerActivityId,
                                             @RequestParam(defaultValue = "1") Integer pageNo,
                                             @RequestParam(defaultValue = "20") Integer pageSize){
        try {
            IPage<VolunteerActivityComment> page =
                    volunteerActivityCommentService.selectActivityAllComment(volunteerActivityId, pageNo, pageSize);
            return ResultGenerator.getSuccessResult(page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }


}

