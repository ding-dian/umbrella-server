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
 * 志愿者活动信息统计
 * </p>
 *
 * @author xiaoyao
 * @since 2021-11-25
 */
@Api(tags = "志愿者信息统计模块")
@RestController
@RequestMapping("/staticInfo")
public class StatisticalInformationController {
    @Autowired
    private VolunteerStatisticalInformationService staticInfoService;

    @ApiOperation("查询接口")
    @GetMapping("getStaticInfo")
    public Result getVolunteerStaticalInformation(Integer volunteerId) {
        try {
            VolunteerStatisticalInformation volunteerStatisticalInformation = staticInfoService.selectVoluteerStaticalInformation(volunteerId);
            return ResultGenerator.getSuccessResult(volunteerStatisticalInformation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }

    @ApiOperation("更新接口")
    @PostMapping("/updateStaticInfo")
    public Result updateVolunteerStaticalInfo(@RequestBody AuditeActivityVo auditeActivity) {
        try {
            staticInfoService.updateVoluteerStaticalInformation(auditeActivity);
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
            return ResultGenerator.getSuccessResult(staticInfoService.getList(params));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }
}

