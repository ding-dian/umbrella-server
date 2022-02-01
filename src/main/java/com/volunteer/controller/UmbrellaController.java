package com.volunteer.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.volunteer.component.RedisOperator;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.service.ClientService;
import com.volunteer.service.UmbrellaBorrowService;
import com.volunteer.service.VolunteerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author: 梁峰源
 * @date: 2022/1/26 11:55
 * Description: 爱心雨伞控制模块
 */
@Api(tags = "爱心雨伞控制模块" )
@Slf4j
@RestController
@RequestMapping("/umbrella")
public class UmbrellaController {

    @Resource
    private ClientService clientService;
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private VolunteerService volunteerService;
    @Resource
    private UmbrellaBorrowService umbrellaBorrowService;

    /**
     * 借取爱心雨伞
     * @param token 用户的token
     */
    @RequestMapping("/borrowUmbrellaByToken")
    @ApiOperation(
            value = "根据Token借取爱心雨伞",
            notes = "首先需要请求爱心雨伞锁机，请求成功后再修改数据库数据")
    @ApiResponses({
            @ApiResponse(code = 200,message = "success"),
            @ApiResponse(code = 400,message = "用户未登录，没有获得token"),
            @ApiResponse(code = 601,message = "锁机开启失败"),
            @ApiResponse(code = 602,message = "用户已经借取过雨伞了，不能再借伞"),
    })
    public Result borrowUmbrellaByToken(@RequestParam String token){
        if (StringUtils.isEmpty(token)) {
            return ResultGenerator.getFailResult("token有误!");
        }
        String jsonStr = redisOperator.get(token);
        if (StringUtils.isEmpty(jsonStr)) {
            return ResultGenerator.getFailResult("请登录后重试");
        }
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        Object openID = jsonObject.get("openid");
        //根据openID拿到用户的数据
        Volunteer volunteer = volunteerService.getByOpenId((String) openID);
        if(ObjectUtil.isNotNull(volunteer.getBorrowUmbrellaDate())){
            //已经借取过雨伞了不能借取
            return new Result().setCode(602).setMessage("已经借取过雨伞了不能借取");
        }
        int result = 0;
        //TODO 向爱心雨伞终端发送借伞请求，请求成功后修改数据库
        if(result == 0){
            //终端响应失败，可能是终端掉线或者设备异常
            return new Result().setCode(601).setMessage("终端响应失败，可能是终端掉线或者设备异常");
        }else {
            //请求成功，修改数据库
            umbrellaBorrowService.borrowByVolunteer(volunteer);
        }
        return ResultGenerator.getSuccessResult();
    }

    /**
     * 还伞
     */
    @RequestMapping("/returnUmbrellaByToken")
    @ApiOperation(
            value = "根据Token归还爱心雨伞",
            notes = "首先需要请求爱心雨伞锁机，请求成功后再修改数据库数据")
    @ApiResponses({
            @ApiResponse(code = 200,message = "success"),
            @ApiResponse(code = 400,message = "用户未登录，没有获得token"),
            @ApiResponse(code = 601,message = "锁机开启失败"),
            @ApiResponse(code = 603,message = "用户没有借取过爱心雨伞，不能归还")
    })
    public Result returnUmbrellaByToken(@RequestParam String token){
        if (StringUtils.isEmpty(token)) {
            return ResultGenerator.getFailResult("token有误!!");
        }
        String jsonStr = redisOperator.get(token);
        if (StringUtils.isEmpty(jsonStr)) {
            return ResultGenerator.getFailResult("请登录后重试!");
        }
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        Object openID = jsonObject.get("openid");
        //根据openID拿到用户的数据
        Volunteer volunteer = volunteerService.getByOpenId((String) openID);
        if(ObjectUtil.isNull(volunteer.getBorrowUmbrellaDate())){
            //用户没有借取过雨伞，不能借取
            return new Result().setCode(603).setMessage("用户没有借取过雨伞，不能借取");
        }
        int result=0;
        //TODO 向爱心雨伞终端发送还伞请求，请求成功后修改数据库
        if(result == 0){
            //终端响应失败，可能是终端掉线或者设备异常
            return new Result().setCode(601).setMessage("终端响应失败，可能是终端掉线或者设备异常");
        }else {
            //请求成功，修改数据库
            umbrellaBorrowService.returnByVolunteer(volunteer);
        }
        return ResultGenerator.getSuccessResult();
    }


    /**
     * 向爱心雨伞终端锁发送数据接口
     */
    @RequestMapping("/sendMessage")
    @ApiOperation(
            value = "根据Token去获取用户信息接口",
            notes = "返回用户的信息",
            produces = "application/json",//用户请求数据类型，类型待定
            consumes = "application/json")//用户响应数据类型，类型待定
    @ApiResponses({
            @ApiResponse(code = 200,message = "success"),
            @ApiResponse(code = 400,message = "用户未登录，没有获得token")
    })
    public void sendMessage(String message) {
        log.info("-----sendMessage--------" + message);
        AsynchronousSocketChannel channel = clientService.getChannel();
        channel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));     //往服务端发送控制命令数据
    }
}
