package com.volunteer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author VernHe
 * @date 2021年12月01日 17:20
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 对于所有请求
        registry.addMapping("/**")
                //是否发送凭证
                .allowCredentials(true)
                //允许的请求的来源
                .allowedOriginPatterns("*")
                //允许的方法
                .allowedMethods("GET","PUT","POST","DELETE","OPTIONS","HEAD")
                //客户端缓存相应的时长，单位s
                .maxAge(3600)
                //允许所有的头
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
