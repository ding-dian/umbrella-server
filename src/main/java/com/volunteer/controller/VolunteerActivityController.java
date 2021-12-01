package com.volunteer.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.VolunteerActivity;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.vo.AuditeActivityVo;
import com.volunteer.entity.vo.Ids;
import com.volunteer.service.VolunteerActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Api(value = "value",tags = {"tags"})
@RestController
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
    @ApiOperation(value = "方法的value")
    @PostMapping("/createActivity")
    @ResponseBody
    public Result createActivity(@RequestBody @ApiParam(name = "志愿者名字",value = "传入json格式",required = true) VolunteerActivity volunteerActivity){
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
    public Result deleteActivity(Integer id){
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

    /**
     * 更新志愿者活动状态
     * @param auditeActivity
     * @return
     */
    @PostMapping("/updateActivityStatus")
    @ResponseBody
    public  Result updateActivityStatus(AuditeActivityVo auditeActivity) {
        try {
            VolunteerActivity volunteerActivity = volunteerActivityService.updateActivityStatus(auditeActivity);
            return ResultGenerator.getSuccessResult(volunteerActivity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }

    /**
     * 查询单个志愿者活动
     * @param id
     * @return
     */
    @GetMapping("/selectOneActivity")
    @ResponseBody
    public Result selectOneActivity(Integer id){
        try {
            VolunteerActivity volunteerActivity=volunteerActivityService.selectOne(id);
            return ResultGenerator.getSuccessResult(volunteerActivity);
        } catch (Exception exception) {
            exception.printStackTrace();
            log.info("系统异常：{}",exception.getMessage());
            return ResultGenerator.getFailResult(exception.getMessage());
        }
    }


    @PostMapping("/updateActivity")
    @ResponseBody
    public Result updateActivity(@RequestBody VolunteerActivity volunteerActivity){
        try {
            int date= volunteerActivityService.updateActivity(volunteerActivity);
            return ResultGenerator.getSuccessResult(date);
        } catch (Exception exception) {
            log.error("系统异常：{}",exception.getMessage());
            return ResultGenerator.getFailResult(exception.getMessage());
        }
    }

    @GetMapping("/selectListActivity")
    @ResponseBody
    public Result selectListActivity(VolunteerActivity volunteerActivity){
        try {
            IPage<VolunteerActivity> data = volunteerActivityService.selectListActivity(volunteerActivity);
            return ResultGenerator.getSuccessResult(data);
        }catch (Exception exception){
            log.error("系统异常：{}",exception.getMessage());
            return ResultGenerator.getFailResult(exception.getMessage());
        }
    }

}

