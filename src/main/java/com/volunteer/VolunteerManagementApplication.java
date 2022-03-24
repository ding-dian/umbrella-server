package com.volunteer;

import com.volunteer.service.KcpService;
import com.volunteer.util.SpringContextUtil;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.*;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @author HeFuRen
 */
@SpringBootApplication
@MapperScan("com.volunteer.mapper")
@PropertySource(value={"file:application-LinuxProd.yml"}) //加载jar包同级目录里面的配置文件
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING) // 解决jmx重复注册bean的问题
public class VolunteerManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(VolunteerManagementApplication.class, args);
        //启动kcp服务与锁机进行连接
        KcpService bean = SpringContextUtil.getBean(KcpService.class);
        bean.initKcpService();

    }


    //https访问，项目上线时启用
    //添加转向类，用户将客户访问的非https协议转向到https协议
    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(80);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(443);
        return connector;
    }
}
