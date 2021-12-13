package com.volunteer.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.vo.Ids;
import com.volunteer.service.VolunteerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Api(tags = "志愿者模块")
@RestController
@RequestMapping("/volunteer")
@Slf4j
public class VolunteerController {
        @Autowired
        VolunteerService volunteerService;

        @ApiOperation("注册接口")
        @PostMapping("/register")
        public Result register(@RequestBody Volunteer register){
            System.out.println("进入Controller");
            try {
                int result = volunteerService.register(register);
                if (result!=-1) {
                    return ResultGenerator.getSuccessResult("注册成功");
                }else{
                    return ResultGenerator.getSuccessResult("请检查用户名，密码！");
                }
            }
            catch (Exception exception) {
                log.error("系统异常：{}",exception.getMessage());
                return ResultGenerator.getSuccessResult("网络异常，请稍后重试");
            }

        }

        @ApiOperation("分页查询接口")
        @GetMapping("/selectList")
        public Result selectList(@RequestBody Volunteer volunteer){
            try {
                IPage<Volunteer> data = volunteerService.selectList(volunteer);
                return ResultGenerator.getSuccessResult(data);
            }catch (Exception exception){
                log.error("系统异常：{}",exception.getMessage());
                return ResultGenerator.getFailResult(exception.getMessage());
            }
        }
        @ApiOperation("删除接口")
        @PostMapping("/deleteOne")
        public Result deleteOne(@RequestParam Integer id){
            try {
                 volunteerService.deleteVolunteer(id);
                return ResultGenerator.getSuccessResult();
            } catch (Exception exception) {
                log.error("系统异常：{}",exception.getMessage());
                return ResultGenerator.getFailResult(exception.getMessage());
            }
        }
        @ApiOperation("批量删除接口")
        @PostMapping("/deleteList")
        public Result deleteList(@RequestBody Ids ids){
            try {
                volunteerService.deleteList(ids.getIds());
                return ResultGenerator.getSuccessResult();
            } catch (Exception exception) {
                log.error("系统异常：{}",exception.getMessage());
                return ResultGenerator.getFailResult("系统异常");
            }
        }
    @ApiOperation("查询接口")
    @GetMapping("/selectOne")
    public Result selectOne(@RequestParam Integer id){
        try {
         Volunteer volunteer=volunteerService.selectOne(id);
            return ResultGenerator.getSuccessResult(volunteer);
        } catch (Exception exception) {
            log.error("系统异常：{}",exception.getMessage());
            return ResultGenerator.getFailResult("系统异常");
        }
    }
    @ApiOperation("更新接口")
    @PostMapping("/update")
    public Result update(@RequestBody Volunteer volunteer){
        try {
          int date= volunteerService.update(volunteer);
            return ResultGenerator.getSuccessResult(date);
        } catch (Exception exception) {
            log.error("系统异常：{}",exception.getMessage());
            return ResultGenerator.getFailResult("系统异常");
        }
    }
}

