package com.volunteer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置类
 * @author VernHe
 * @date 2021年12月31日 22:28
 */
@Getter
@Setter
@Configuration
@EnableAsync
@ConfigurationProperties(prefix = "task.pool")
public class ThreadPoolConfig {

    /**
     * 核心线程数（默认线程数）
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maxPoolSize;
    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private int keepAliveSeconds;
    /**
     * 缓冲队列大小
     */
    private int queueCapacity;
    /**
     * 线程池名前缀
     */
    private String threadNamePrefix;

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setThreadNamePrefix(threadNamePrefix);
        return taskExecutor;
    }
}
