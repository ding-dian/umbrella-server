package com.volunteer.protocol.service2Lock.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 20:43
 * Description: 锁机设置，ex：芯片开关、WiFi接入点、这些值传给伞机需要过滤为空的字段
 * 这里每一个属性都要用类封装起来，方便硬件同学对类进行遍历，因为c语言真的很麻烦！！！！！！！！！！！
 */
@Data
@Accessors(chain = true)
public class LockMachineConfig implements Config{
    /**
     * CPU频率 单位：M
     */
    private cpu_freq cpu_freq;
    @Data
    @AllArgsConstructor
    public static class cpu_freq implements Config{
        private Integer value;
    }
    /**
     * 休眠时间 单位：秒
     */
    private deep_sleep deep_sleep;
    @Data
    @AllArgsConstructor
    public static class deep_sleep implements Config{
        private Integer value;
    }
    /**
     * 上报间隔，单位：毫秒
     */
    private reporting_duration reporting_duration;
    @Data
    @AllArgsConstructor
    public static class reporting_duration implements Config{
        private Integer value;
    }
    /**
     * 下发WiFi配置，这个类单独放出去了，太多了
     */
    private LockWiFiConfig wifi_config;
    /**
     * 16位微秒级别时间戳
     */
    private timeStamp timeStamp;
    @Data
    @AllArgsConstructor
    public static class timeStamp implements Config{
        private String value;
    }
    /**
     * 是否自动更新时间，自动从给定ntp服务器中拿取
     */
    private auto_update_time auto_update_time;
    @Data
    @AllArgsConstructor
    public static class auto_update_time implements Config{
        private Boolean value;
    }
    /**
     * ntp服务器地址，用以进行自动时间矫正
     */
    private ntp_server_hostname ntp_server_hostname;
    @Data
    @AllArgsConstructor
    public static class ntp_server_hostname implements Config{
        private String value;
    }
    /**
     * 服务器域名
     */
    private umbrella_server_hostname umbrella_server_hostname;
    @Data
    @AllArgsConstructor
    public static class umbrella_server_hostname implements Config{
        private String value;
    }
}
