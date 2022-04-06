package com.volunteer.protocol.service2Lock;

import com.volunteer.protocol.LockDeviceDic;
import com.volunteer.protocol.service2Lock.Operation.LEDOperation;
import com.volunteer.protocol.service2Lock.Operation.LockOperation;
import com.volunteer.protocol.service2Lock.config.*;

/**
 * @author: 梁峰源
 * @date: 2022/4/3 15:54
 * Description:
 */
public class test {
    public static void main(String[] args) {
        LockWiFiConfig lockWiFiConfig = new LockWiFiConfig();
        lockWiFiConfig
                .setSsid("HNIT_Teacher")
                .setPassword("xxxxxxxx")
                .setProtocol("802.11ac")
                .setSecurity_type("WPA2-个人")
                .setFrequency_band("2.4GHz")
                .setChannel(161)
                .setDns_addr(new LockWiFiConfig.DnsAddr(4, new int[]{192, 198, 90, 190}))
                .setIpv4_addr(new int[]{192, 168, 1, 1})
                .setMac(new int[]{192, 168, 1, 1})
                .setIp_assigned_type("static");
        LockMachineConfig lockMachineConfig = new LockMachineConfig();
        lockMachineConfig.setWifi_config(lockWiFiConfig)
                .setAuto_update_time(new LockMachineConfig.auto_update_time(true))
                .setCpu_freq(new LockMachineConfig.cpu_freq(34))
                .setUmbrella_server_hostname(new LockMachineConfig.umbrella_server_hostname("www.volunteer.com"))
                .setNtp_server_hostname(new LockMachineConfig.ntp_server_hostname("xxxx"));
        LockMachineConfig lockMachineConfig1 = new LockMachineConfig();
        lockMachineConfig1.setDeep_sleep(new LockMachineConfig.deep_sleep(10));
        Service2LockVo service2LockVo = new Service2LockVo()
                .setId(LockDeviceDic.WIFI_CODE)
                .setPost_type(LockDeviceDic.PostType.config)
                .setTimestamp(System.currentTimeMillis() + "")
                .setContent(new DeviceConfig(new Config[]{lockMachineConfig, lockMachineConfig1}));
        System.out.println(ConfigFilter.filter(service2LockVo));

        Service2LockVo service2LockVo2 = new Service2LockVo()
                .setId(3578862)
                .setPost_type(LockDeviceDic.PostType.control)
                .setContent(new ServiceControlOperation(
                        new ServiceControlOperation.Target(LockDeviceDic.SELF,LockDeviceDic.SELF_CODE),
                        new LockOperation(true,60 * 5)
                ))
                .setTimestamp(System.currentTimeMillis() + "");
        System.out.println((ConfigFilter.filter(service2LockVo2) + "") instanceof String);
    }
}
