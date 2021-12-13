package com.volunteer.controller;


import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.vo.SignUpVo;
import com.volunteer.service.SignUpRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  报名记录控制器
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
     * @return
     */
    @ApiOperation("报名接口")
    @PostMapping("/signUpActivity")
    public Result signUpActivity(@RequestBody SignUpVo query) {
        try {
            signUpRecordService.signUp(query);
            return ResultGenerator.getSuccessResult("success");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return ResultGenerator.getFailResult("系统错误，请联系管理员！");
    }
}

