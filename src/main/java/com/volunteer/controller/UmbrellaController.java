package com.volunteer.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.volunteer.component.RedisOperator;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.common.UmbrellaDic;
import com.volunteer.entity.vo.UmbrellaHistoryListVo;
import com.volunteer.entity.vo.UmbrellaOrderListVo;
import com.volunteer.entity.vo.UmbrellaOrderVo;
import com.volunteer.service.KcpService;
import com.volunteer.service.UmbrellaBorrowService;
import com.volunteer.service.VolunteerService;
import com.volunteer.util.SendMailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

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

//    @Resource
//    private ClientService clientService;
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private VolunteerService volunteerService;
    @Resource
    private UmbrellaBorrowService umbrellaBorrowService;
    @Resource
    private SendMailUtil sendMailUtil;
//    @Resource
//    private KcpService kcpService;
    /**
     * 借取爱心雨伞
     * @param token 用户的token
     */
    @GetMapping("/borrowUmbrellaByToken")
    @ApiOperation(
            value = "根据Token借取爱心雨伞",
            notes = "首先需要请求爱心雨伞锁机，请求成功后再修改数据库数据")
    @ApiResponses({
            @ApiResponse(code = 200,message = "success"),
            @ApiResponse(code = 400,message = "用户未登录，没有获得token"),
            @ApiResponse(code = 601,message = "锁机开启失败"),
            @ApiResponse(code = 602,message = "用户已经借取过雨伞了，不能再借伞"),
            @ApiResponse(code = 604,message = "用户未在规定时间内取走伞"),
    })
    public synchronized Result borrowUmbrellaByToken(@RequestParam String token){
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
        //TODO 向爱心雨伞终端发送借伞请求，请求成功后修改数据库
//        String flag = KcpService.sendUnlockMsg2Lock();
//        if(UmbrellaDic.UNLOCK_FAIL_MSG.equals(flag)){
//            return new Result().setCode(602).setMessage("锁机开启失败，系统异常");
//        }
//        if(UmbrellaDic.UNLOCK_OVERTIME_MSG.equals(flag)){
//            return new Result().setCode(604).setMessage("用户未在规定时间内取走伞");
//        }
        int result = 0;
        try {
            result = umbrellaBorrowService.borrowByVolunteer(volunteer);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if(result == -1){
            //用户已经借取过爱心雨伞了，不能借取
            return new Result().setCode(602).setMessage("用户已经借取过爱心雨伞了，不能借取");
        }else {
            return ResultGenerator.getSuccessResult();
        }
    }

    /**
     * 还伞
     */
    @GetMapping("/returnUmbrellaByToken")
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
        int result=umbrellaBorrowService.returnByVolunteer(volunteer);
        //TODO 向爱心雨伞终端发送还伞请求，请求成功后修改数据库
        if(result == -1){
            //终端响应失败，可能是终端掉线或者设备异常
            return new Result().setCode(601).setMessage("终端响应失败，可能是终端掉线或者设备异常");
        }else {
            //请求成功
            return ResultGenerator.getSuccessResult();
        }
    }

    /**
     * 从redis中查询所有实时借阅雨伞的信息
     * @param pageNo 页号，大于1
     * @param pageSize 每页多少数据，默认20条
     * @return 返回Result
     */
    @GetMapping("/selectBorrowList")
    @ApiOperation(value = "查询借伞用户信息列表",response = UmbrellaOrderVo.class)
    public Result selectBorrowList(Integer pageNo, Integer pageSize){
        UmbrellaOrderListVo listVo;
        try {
            listVo = umbrellaBorrowService.selectBorrow(pageNo, pageSize);
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error("查询借伞用户信息列表失败：{}",e.getMessage());
            return ResultGenerator.getFailResult(e.getMessage());
        }
        JSON jsonStr = JSONUtil.parse(listVo);
        return ResultGenerator.getSuccessResult(jsonStr);
    }
    /**
     * 查询超时借伞用户信息列表
     * @param pageNo 页号，大于1
     * @param pageSize 每页多少数据，默认20条
     * @return 返回Result
     */
    @GetMapping("/selectOverTimeList")
    @ApiOperation(value = "查询超时借伞用户信息列表",response = UmbrellaOrderVo.class)
    public Result selectOverTimeList(Integer pageNo, Integer pageSize){
        UmbrellaOrderListVo listVo;
        try {
            listVo = umbrellaBorrowService.selectOvertime(pageNo, pageSize);
        } catch (Exception e) {
            return ResultGenerator.getFailResult(e.getMessage());
        }
        JSON jsonStr = JSONUtil.parse(listVo);
        return ResultGenerator.getSuccessResult(jsonStr);
    }

    /**
     * 从数据库中查询历史用户借伞信息
     * @param pageNo 页号，大于1
     * @param pageSize 每页多少数据，默认20条
     * @return 返回Result
     */
    @GetMapping("/selectHistoryBorrow")
    @ApiOperation(value = "从数据库中查询历史用户借伞信息",response = UmbrellaOrderVo.class)
    public Result selectHistoryBorrow(Integer pageNo, Integer pageSize){
        UmbrellaHistoryListVo listVo;
        try {
            listVo = umbrellaBorrowService.selectHistoryAll(pageNo, pageSize);
        } catch (Exception e) {
        	return ResultGenerator.getFailResult(e.getMessage());
        }
        JSON jsonStr = JSONUtil.parse(listVo);
        return ResultGenerator.getSuccessResult(jsonStr);
    }

    /**
     * 从redis中删除一条超时用户的数据
     * @param key 用户信息对应在redis中的key
     * @return 返回Result
     */
    @GetMapping("/deleteOvertime")
    @ApiOperation(value = "从redis中删除一条超时用户的数据")
    public Result deleteOvertime(String key){
        if(ObjectUtil.isNull(key) || StringUtils.isEmpty(key)){
            return ResultGenerator.getFailResult("用户key为空，删除失败");
        }
        try {
            umbrellaBorrowService.deleteOvertime(key);
        } catch (Exception e) {
            return ResultGenerator.getFailResult(e.getMessage());
        }
        return ResultGenerator.getSuccessResult();
    }


    /**
     * 由网页端发送邮件给用户
     * @param mailTo 接收人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return 返回Result
     */
    @GetMapping("/sendAlarmEmail")
    @ApiOperation(value = "由网页端发送邮件给用户")
    public Result sendAlarmEmail(String mailTo,String subject ,String content){
        if(ObjectUtil.isNull(mailTo) || StringUtils.isEmpty(mailTo)){
            return ResultGenerator.getFailResult("收件人邮箱为空");
        }
        try {
            sendMailUtil.sendOverTimeAlarm(new String[]{mailTo},subject,content);
        } catch (Exception e) {
            return ResultGenerator.getFailResult(e.getMessage());
        }
        return ResultGenerator.getSuccessResult();
    }


//    /**
//     * 向爱心雨伞终端锁发送数据接口
//     */
//    @RequestMapping("/sendMessage")
//    @ApiOperation(
//            value = "根据Token去获取用户信息接口",
//            notes = "返回用户的信息",
//            produces = "application/json",//用户请求数据类型，类型待定
//            consumes = "application/json")//用户响应数据类型，类型待定
//    @ApiResponses({
//            @ApiResponse(code = 200,message = "success"),
//            @ApiResponse(code = 400,message = "用户未登录，没有获得token")
//    })
//    public void sendMessage(String message) {
//        log.info("-----sendMessage--------" + message);
//        AsynchronousSocketChannel channel = clientService.getChannel();
//        channel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));     //往服务端发送控制命令数据
//    }
}
