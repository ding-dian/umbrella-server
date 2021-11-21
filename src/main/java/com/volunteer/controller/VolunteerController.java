package com.volunteer.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.vo.Ids;
import com.volunteer.service.VolunteerService;
import com.volunteer.entity.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Controller
@RequestMapping("/volunteer")
@Slf4j
@CrossOrigin
public class VolunteerController {
        @Autowired
        VolunteerService volunteerService;

        @PostMapping("/register")
        @ResponseBody
        public Result register(Volunteer register){
            try {
                int result = volunteerService.register(register);
                if (result!=-1) {
                    return ResultGenerator.getSuccessResult("注册成功");
                }else{
                    return ResultGenerator.getSuccessResult("请检查用户名，密码！");
                }
            } catch (Exception exception) {
                log.error("系统异常：{}",exception.getMessage());
                return ResultGenerator.getSuccessResult("网络异常，请稍后重试");
            }

        }

        @GetMapping("/selectList")
        @ResponseBody
        public Result selectList(Volunteer volunteer){
            try {
                IPage<Volunteer> data = volunteerService.selectList(volunteer);
                return ResultGenerator.getSuccessResult(data);
            }catch (Exception exception){
                log.error("系统异常：{}",exception.getMessage());
                return ResultGenerator.getFailResult(exception.getMessage());
            }
        }

        @PostMapping("/deleteOne")
        @ResponseBody
        public Result deleteOne( Integer id){
            try {
                 volunteerService.deleteVolunteer(id);
                return ResultGenerator.getSuccessResult();
            } catch (Exception exception) {
                log.error("系统异常：{}",exception.getMessage());
                return ResultGenerator.getFailResult(exception.getMessage());
            }
        }

        @PostMapping("/deleteList")
        @ResponseBody
        public Result deleteList(@RequestBody Ids ids){
            try {
                volunteerService.deleteList(ids.getIds());
                return ResultGenerator.getSuccessResult();
            } catch (Exception exception) {
                log.error("系统异常：{}",exception.getMessage());
                return ResultGenerator.getFailResult("系统异常");
            }
        }

    @GetMapping("/selectOne")
    @ResponseBody
    public Result selectOne(Integer id){
        try {
         Volunteer volunteer=volunteerService.selectOne(id);
            return ResultGenerator.getSuccessResult(volunteer);
        } catch (Exception exception) {
            log.error("系统异常：{}",exception.getMessage());
            return ResultGenerator.getFailResult("系统异常");
        }
    }
    @PostMapping("/update")
    @ResponseBody
    public Result update(Volunteer volunteer){
        try {
          int date= volunteerService.update(volunteer);
            return ResultGenerator.getSuccessResult(date);
        } catch (Exception exception) {
            log.error("系统异常：{}",exception.getMessage());
            return ResultGenerator.getFailResult("系统异常");
        }
    }
}

