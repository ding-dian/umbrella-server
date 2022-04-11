package com.volunteer.controller;


import com.volunteer.entity.AdminInfo;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.service.AdminInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoyao
 * @since 2021-12-06
 */
@Api(tags = "管理员模块")
@RestController
@RequestMapping("/adminInfo")
public class AdminInfoController {
    @Autowired
    AdminInfoService adminInfoService;

    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public Result loginAdmin(@RequestBody String username,
                             @RequestBody String password) {
        try {
            String token = adminInfoService.adminLogin(username, password);
            return  ResultGenerator.getSuccessResult(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }

    @ApiOperation(value = "注销接口")
    @GetMapping("/logout")
    public Result logout(@RequestBody String token){
        try {
             adminInfoService.logout(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultGenerator.getSuccessResult("注销成功");
    }

    @ApiOperation(value = "注册接口")
    @PostMapping("/register")
    public Result register(@RequestBody AdminInfo adminInfo){
        try {
            adminInfoService.register(adminInfo);
            return ResultGenerator.getSuccessResult("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }
}

