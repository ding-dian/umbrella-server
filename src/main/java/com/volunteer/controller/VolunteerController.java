package com.volunteer.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.volunteer.entity.Volunteer;
import com.volunteer.service.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class VolunteerController {
        @Autowired
        VolunteerService volunteerService;

        @PostMapping("/register")
        public ResponseEntity register( Volunteer register){

            try {
                int result = volunteerService.register(register);
                if (result!=-1) {
                    return ResponseEntity.ok("注册成功");
                }else{
                    return ResponseEntity.badRequest().body("请检查用户名，密码！");
                }

            } catch (Exception exception) {
                log.error("系统异常：{}",exception.getMessage());
                return ResponseEntity.badRequest().body("网络异常，请稍后重试");
            }

        }

        @GetMapping("/selectList")
        @ResponseBody
        public ResponseEntity selectList(Volunteer volunteer){
            try {
                IPage<Volunteer> data = volunteerService.selectList(volunteer);
                return ResponseEntity.ok(data);
            }catch (Exception exception){
                log.error("系统异常：{}",exception.getMessage());
                return ResponseEntity.ok("网络异常，请稍后重试");
            }


        }
}

