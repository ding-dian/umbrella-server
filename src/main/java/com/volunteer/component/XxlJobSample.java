package com.volunteer.component;

import com.volunteer.service.VolunteerActivityService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务执行器（Xxl-job方式）
 */
@Component
public class XxlJobSample {

    @Autowired
    private VolunteerActivityService activityService;

    /**
     * 更新活动状态的定时任务
     *
     * @return
     */
    @XxlJob("updateActivityStatusJob")
    public ReturnT<String> updateActivityStatusJob(String value) {
        XxlJobLogger.log("更新活动状态的定时任务开始执行");
        try {
            activityService.updateActivityStatus();
        } catch (Exception e) {
            XxlJobLogger.log("定时任务执行失败");
            return ReturnT.FAIL;
        }
        XxlJobLogger.log("定时任务执行成功");
        return ReturnT.SUCCESS;
    }
}
