package com.volunteer.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.volunteer.entity.AdminInfo;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.vo.LoginVo;
import com.volunteer.service.AdminInfoService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoyao
 * @since 2021-12-06
 */
@Controller
@RequestMapping("/adminInfo")
public class AdminInfoController {
    @Autowired
    AdminInfoService adminInfoService;

    @PostMapping("/login")
    @ResponseBody
    public Result loginAdmin(String username, String password) {
        try {
            String token = adminInfoService.adminLogin(username, password);
            return  ResultGenerator.getSuccessResult(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }
    @GetMapping("/logout")
    @ResponseBody
    public Result logout(String token){
        try {
             adminInfoService.logout(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultGenerator.getSuccessResult("注销成功");
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(AdminInfo adminInfo){
        try {
            adminInfoService.register(adminInfo);
            return ResultGenerator.getSuccessResult("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }
}

