package com.volunteer.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.volunteer.entity.Admin;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * @author VernHe
 * @date 2021年11月20日 13:31
 * @Description 测试时临时使用的Controller
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class AdminController {

    //@ApiImplicitParam(name = "登录的表单信息",dataTypeClass = LoginVo.class)
    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        System.out.println(loginVo);
        JSONObject jsonObject = JSONUtil.createObj().put("token","sadsaewead");
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    @ApiOperation(value = "得到token信息接口")
    @GetMapping("/info")
    public Result getInfo(@PathParam("token") String token) {
        System.out.println("接收到参数：" + token);
        Admin admin = new Admin();
        admin.setUserName("admin");
        admin.setDescription("管理员");
        admin.setId(1);
        admin.setRoles(new String[]{"admin"});
        return ResultGenerator.getSuccessResult(admin);
    }
}
