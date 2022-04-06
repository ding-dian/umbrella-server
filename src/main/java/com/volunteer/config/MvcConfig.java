package com.volunteer.config;

import com.volunteer.biz.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author: 梁峰源
 * @date: 2022/4/4 13:00
 * @Description: 添加自定义拦截器
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {
    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.accessLimitInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
