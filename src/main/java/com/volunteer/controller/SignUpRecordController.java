package com.volunteer.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.volunteer.entity.SignUpRecord;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.common.SignUpStatus;
import com.volunteer.entity.vo.SignUpRecordVo;
import com.volunteer.entity.vo.SignUpVo;
import com.volunteer.service.SignUpRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 报名记录控制器
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */

@Api(tags = "报名活动模块")
@RestController
@RequestMapping("/signUpRecord")
public class SignUpRecordController {

    @Autowired
    private SignUpRecordService signUpRecordService;

    /**
     * 志愿者报名参加志愿活动接口
     *
     * @return
     */
    @ApiOperation("报名接口")
    @PostMapping("/signUpActivity")
    public Result signUpActivity(@RequestBody SignUpVo query) {
        if (ObjectUtil.isNull(query)) {
            return ResultGenerator.getFailResult("参数有误");
        }
        try {
            int signUpStatus = signUpRecordService.signUp(query);
            if (signUpStatus == SignUpStatus.NOT_LOGIN) {
                return ResultGenerator.getFailResult("请重新登陆");
            } else if (signUpStatus == SignUpStatus.ALREADY_SIGNED_UP) {
                return ResultGenerator.getFailResult("您已经报名",601);
            } else if (signUpStatus == SignUpStatus.SIGN_UP_FAIL) {
                return ResultGenerator.getFailResult("报名失败，请稍后重试");
            }
            return ResultGenerator.getSuccessResult("报名成功");
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultGenerator.getFailResult("系统错误，请联系管理员！");
        }
    }

    /// TODO 小程序端完成活动取消报名功能，将‘已报名’改为‘取消报名’即可
    /**
     * 志愿者取消报名志愿活动接口
     *
     * @return
     */
    @ApiOperation("取消报名接口")
    @PostMapping("/cancelRegistration")
    public Result cancelRegistration(@RequestBody SignUpVo query) {
        try {
            signUpRecordService.cancelRegistration(query);
            return ResultGenerator.getSuccessResult("success");
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultGenerator.getFailResult(exception.getMessage());
        }
    }

    /**
     * 检查报名状态
     * @return  true：已报名，false：未报名
     */
    @ApiOperation("报名状态查询接口")
    @PostMapping("/checkState")
    public Result checkSignUpState(@RequestBody SignUpVo query) {
        try {
            if (StringUtils.isNotEmpty(query.getToken()) && ObjectUtil.isNotNull(query.getActivityId())) {
                boolean result = signUpRecordService.checkSignUpState(query);
                return ResultGenerator.getSuccessResult(result);
            } else {
                return ResultGenerator.getFailResult("请检查参数后重试");
            }
        } catch (Exception e) {
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }

    /**
     * 获取报名列表
     * @return  true：已报名，false：未报名
     */
    @ApiOperation("报名记录查询接口")
    @GetMapping("/getList")
    public Result getList(SignUpRecordVo signUpRecordVo) {
        try {
            Page<SignUpRecordVo> data = signUpRecordService.getList(signUpRecordVo);
            return ResultGenerator.getSuccessResult(data);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }

    /**
     * 根据ID删除报名记录
     * @return  删除结果
     */
    @ApiOperation("根据ID查询报名的详细信息")
    @DeleteMapping("/deleteById/{id}")
    public Result getById(@PathVariable Integer id) {
        try {
            signUpRecordService.deleteRecordById(id);
            return ResultGenerator.getSuccessResult();
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }


}

