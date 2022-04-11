package com.volunteer.config;

import com.volunteer.service.VolunteerActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务配置类（@Scheduled方式）
 */
@EnableScheduling
@Configuration
public class CronConfig {

//    @Autowired
//    private VolunteerActivityService volunteerActivityService;

    /**
     * 更新志愿活动的定时任务
     */
//    @Scheduled(cron = "*/60 * * * * ?")
//    public void testCron() {
//        volunteerActivityService.updateActivityStatus();
//    }
}
