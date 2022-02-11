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

import java.util.Objects;

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
    @GetMapping("/getById/{volunteerId}")
    public Result getVolunteerStaticalInformation(@PathVariable int volunteerId) {
        try {
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

    @ApiOperation("列表查询接口")
    @GetMapping("/getList")
    public Result getList(VolunteerStatisticalInformation params) {
        try {
            if (Objects.isNull(params.getPageNo())) {
                params.setPageNo(1);
            }
            if (Objects.isNull(params.getPageSize())) {
                params.setPageSize(10);
            }
            return ResultGenerator.getSuccessResult(volunteerStatisticalInformationService.getList(params));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }
}

