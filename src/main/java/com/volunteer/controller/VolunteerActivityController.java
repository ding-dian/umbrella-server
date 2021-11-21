package com.volunteer.controller;



import com.volunteer.entity.VolunteerActivity;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.vo.Ids;
import com.volunteer.service.VolunteerActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Controller
@RequestMapping("/volunteerActivity")
@Slf4j
public class VolunteerActivityController {
    @Autowired
    private VolunteerActivityService volunteerActivityService;

    /**
     * 创建志愿者活动
     * @param volunteerActivity
     * @return
     */

    @PostMapping("/createActivity")
    @ResponseBody
    public Result createActivity(VolunteerActivity volunteerActivity){
        try {
            int result = volunteerActivityService.createActivity( volunteerActivity);
            if (result==1){
                return ResultGenerator.getSuccessResult("活动添加成功");
            }else {
                return ResultGenerator.getFailResult("请检查活动");
            }
        } catch (Exception exception) {
            log.error("系统异常：{}",exception.getMessage());
            return ResultGenerator.getSuccessResult(exception.getMessage());
        }

    }

    /**
     * 删除志愿者活动
     * @param id
     * @return
     */
    @PostMapping("/deleteActivity")
    @ResponseBody
    public Result deleteActivity( Integer id){
        try {
            volunteerActivityService.deleteActivity(id);
            return ResultGenerator.getSuccessResult();
        } catch (Exception exception) {
            log.error("系统异常：{}",exception.getMessage());
            return ResultGenerator.getFailResult("系统异常");
        }
    }

    /**
     * 批量删除志愿者活动
     * @param ids
     * @return
     */
    @PostMapping("/deleteListActivity")
    @ResponseBody
    public Result deleteListActivity(@RequestBody Ids ids){
        System.out.println(ids);
        try {
            volunteerActivityService.deleteListActivity(ids.getIds());
            return ResultGenerator.getSuccessResult();
        } catch (Exception exception) {
            log.error("系统异常：{}",exception.getMessage());
            return ResultGenerator.getFailResult("系统异常");
        }
    }

}

