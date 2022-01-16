package com.volunteer.controller;

import com.volunteer.component.RedisOperator;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.service.UploadService;
import com.volunteer.service.VolunteerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Api(tags = "文件上传模块(新-OOS)")
@RestController
@RequestMapping("/oos")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private VolunteerService volunteerService;

    @ApiOperation("志愿者头像更新接口（测试接口）")
    @PostMapping("/upload")
    public Result uploadImage(String token, MultipartFile avatarImg) {
        // 判断是否登录
//        Volunteer volunteer = redisOperator.getObjectByToken(token, Volunteer.class);
//        if (Objects.isNull(volunteer)) {
//            return ResultGenerator.getFailResult("请登录后重试");
//        }
        if (Objects.isNull(avatarImg)) {
            return ResultGenerator.getFailResult("请选择上传的图片后重试");
        }
        // 测试代码
        Volunteer volunteer = volunteerService.getById(74);
        String imgUrl = null;
        try {
            imgUrl = uploadService.uploadVolunteerAvatar(token,volunteer,avatarImg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult("图片上传失败，请稍后重试");
        }
        return ResultGenerator.getSuccessResult(imgUrl);
    }

    /**
     * 测试 xxl-job的接口
     *
     * @return
     */
    @PostMapping("/test")
    public Result testXxlJob() {
        System.out.println("被调用了");
        return ResultGenerator.getSuccessResult();
    }
}
