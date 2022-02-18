package com.volunteer.component;

import com.volunteer.service.UmbrellaBorrowService;
import com.volunteer.service.VolunteerActivityService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务执行器（Xxl-job方式）
 * 访问地址:<a href="http://localhost:8081/xxl-job-admin/"/>
 * userName:admin
 * password:123456
 *  1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 *  2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 *  3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 */
@Component
public class XxlJobSample {

    @Autowired
    private VolunteerActivityService activityService;
    @Autowired
    private UmbrellaBorrowService umbrellaBorrowService;

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

    /**
     * 更新redis中爱心雨伞的借取时间
     */
    @XxlJob("updateBorrowDurationJob")
    public ReturnT<String> updateBorrowDurationJob(String param){
        XxlJobLogger.log("爱心雨伞的借取时间更新活动状态的定时任务开始执行");
        try {
        	umbrellaBorrowService.updateBorrowDurationJob();
        } catch (Exception e) {
            XxlJobLogger.log("定时任务执行失败");
            return ReturnT.FAIL;
        }
        XxlJobLogger.log("定时任务执行成功");
        return ReturnT.SUCCESS;
    }

}
