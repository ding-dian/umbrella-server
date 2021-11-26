package com.volunteer.controller;


import com.volunteer.entity.VolunteerStatisticalInformation;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.vo.AuditeActivityVo;
import com.volunteer.service.VolunteerStatisticalInformationService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoyao
 * @since 2021-11-25
 */
@Controller
@RequestMapping("/volunteerStatisticalInformation")
public class VolunteerStatisticalInformationController {
    @Autowired
    private VolunteerStatisticalInformationService volunteerStatisticalInformationService;

    @GetMapping("getselectVoluteerStaticalInformation")
    @ResponseBody
    public Result getVoluteerStaticalInformation(@Param("volunteerId")Integer volunteerId){
        try {
            System.out.println(volunteerId);
            VolunteerStatisticalInformation volunteerStatisticalInformation = volunteerStatisticalInformationService.selectVoluteerStaticalInformation(volunteerId);
                return ResultGenerator.getSuccessResult(volunteerStatisticalInformation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }
    @PostMapping("/updateVoluteerStaticalInfo")
    @ResponseBody
    public Result updateVoluteerStaticalInfo(@RequestBody AuditeActivityVo auditeActivity){
        try {
            volunteerStatisticalInformationService.updateVoluteerStaticalInformation(auditeActivity);
            return ResultGenerator.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }

    }
}

