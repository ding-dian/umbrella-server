package com.volunteer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author VernHe
 * @date 2021年11月07日 19:39
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket creatRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //swagger的扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.volunteer"))
                //swagger的路径匹配规则
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("湖工志愿者管理平台API文档")
                .description("API文档")
                .version("version 1.0")
                .build();
    }

}
