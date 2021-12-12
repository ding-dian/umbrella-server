package com.volunteer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author VernHe
 * @date 2021年12月13日 0:06
 * @Description API文档配置类,通过 http://localhost:8080/doc.html 打开API文档
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("湖工志愿者管理平台")
                        .description("# 湖工志愿者管理平台API文档")
                        .termsOfServiceUrl("47.101.206.1")
                        .contact(new Contact("VernHe,逍遥","47.101.206.1","vernhe@foxmail.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("2.0版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.volunteer.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
