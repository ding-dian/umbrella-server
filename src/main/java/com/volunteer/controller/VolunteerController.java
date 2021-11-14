package com.volunteer.controller;


import com.volunteer.entity.Volunteer;
import com.volunteer.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.Map;

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
public class VolunteerController {
        @Autowired
        VolunteerService volunteerService;
        public ResponseEntity register(@RequestBody Volunteer register){

            try {
                volunteerService.register(register);
                return ResponseEntity.ok("success");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return ResponseEntity.badRequest().body("请检查用户名，密码和手机号是否合法！");

        }
}

