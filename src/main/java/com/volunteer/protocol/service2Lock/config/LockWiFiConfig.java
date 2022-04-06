package com.volunteer.protocol.service2Lock.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 20:57
 * Description: wifi连接配置，需求为空则不发送字段，这个配置有点多久不作为内部类存在了
 */
@Data
@Accessors(chain = true)
public class LockWiFiConfig implements Config {
    /**
     * wifi名
     */
    private String ssid;
    /**
     * 密码
     */
    private String password;
    /**
     * wifi协议标准，ex：802.11ac
     */
    private String protocol;
    /**
     * 安全类型，例如：WPA2-个人
     */
    private String security_type;
    /**
     * 网络频带 ex:5GHz\2.4GHz
     */
    private String frequency_band;
    /**
     * 信道 ex：161
     */
    private int channel;
    /**
     * ipv6_addr ex：fe80::45d3:480b:d918:d7c8%19,硬件格式,需要转成十进制
     */
    private int[] ipv6_addr;
    /**
     * ipv4_addr ex:{192,168,1,11}
     */
    private int[] ipv4_addr;
    /**
     * DnsAddr,包含ip_version和addr
     */
    private DnsAddr dns_addr;
    /**
     * mac地址，ex：08：00：20：0A：8C：6D
     */
    private int[] mac;
    /**
     * ip分配的形式 static、dhcp
     */
    private String ip_assigned_type;

    @Data
    @AllArgsConstructor
    public static class DnsAddr implements Config{
        /**
         * 4 ipv4  6 ipv6
         */
        private int ip_version;
        /**
         * ipv6_addr or ipv6_addr ex：xxxxxxx:xxxxxxxx:xxxxxxxx:xxxxxxxx,硬件格式
         */
        private int[] addr;
    }

}
