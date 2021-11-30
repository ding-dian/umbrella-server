package com.volunteer.entity.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VmProperties {

    public static final String HTTP_PROTOCOL = "http://";

    public static final String BACKSLASH = "/";

    public static final String COLON = ":";


    public static String VM_HOST;


    public static String VM_NGINX_PORT;

    @Value("${vm.host}")
    public void setVmHost(String vmHost) {
        VM_HOST = vmHost;
    }

    @Value("${vm.nginx-port}")
    public void setVmNginxPort(String vmNginxPort) {
        VM_NGINX_PORT = vmNginxPort;
    }
}
