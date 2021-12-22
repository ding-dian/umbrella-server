package com.volunteer.controller;

import com.volunteer.entity.VolunteerStatisticalInformation;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.vo.AuditeActivityVo;
import com.volunteer.service.VolunteerStatisticalInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaoyao
 * @since 2021-11-25
 */
@Api(tags = "志愿者信息统计模块")
@RestController
@RequestMapping("/volunteerStatisticalInformation")
public class StatisticalInformationController {
    @Autowired
    private VolunteerStatisticalInformationService volunteerStatisticalInformationService;

    @ApiOperation("查询接口")
    @GetMapping("getselectVolunteerStaticalInformation")
    public Result getVolunteerStaticalInformation(@RequestParam int volunteerId) {
        try {
            System.out.println(volunteerId);
            VolunteerStatisticalInformation volunteerStatisticalInformation = volunteerStatisticalInformationService.selectVoluteerStaticalInformation(volunteerId);
            return ResultGenerator.getSuccessResult(volunteerStatisticalInformation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }

    @ApiOperation("更新接口")
    @PostMapping("/updateVolunteerStaticalInfo")
    public Result updateVolunteerStaticalInfo(@RequestBody AuditeActivityVo auditeActivity) {
        try {
            volunteerStatisticalInformationService.updateVoluteerStaticalInformation(auditeActivity);
            return ResultGenerator.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }

    }
}

