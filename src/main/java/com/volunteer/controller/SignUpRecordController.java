package com.volunteer.controller;


import com.volunteer.entity.vo.SignUpVo;
import com.volunteer.service.SignUpRecordService;
import com.volunteer.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  报名记录控制器
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@RestController
@RequestMapping("/signUpRecord")
public class SignUpRecordController {

    @Autowired
    private SignUpRecordService signUpRecordService;

    /**
     * 志愿者报名参加志愿活动接口
     * @return
     */
    @PostMapping("/signUpActivity")
    @ResponseBody
    public ResponseEntity signUpActivity(@RequestBody SignUpVo query) {
        try {
            signUpRecordService.signUp(query);
            return ResponseEntity.ok("success");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return ResponseEntity.badRequest().body("系统错误，请联系管理员！");
    }
}

