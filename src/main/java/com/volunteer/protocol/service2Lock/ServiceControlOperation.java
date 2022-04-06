package com.volunteer.protocol.service2Lock;

import com.volunteer.protocol.service2Lock.Operation.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 20:17
 * Description: 服务器发送给锁机的控制帧
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ServiceControlOperation {
    /**
     * 设备定位,请查看字典 {@code LockDeviceDic}
     */
    private Target target;
    /**
     * 操作
     */
    private Operation operation;

    /**
     * 设备对象
     */
    @Data
    @AllArgsConstructor
    public static class Target{
        /**
         * 设备名称，请在字典{@code LockDeviceDic}中获取
         */
        private String device_name;
        /**
         * 设备识别代码，请在字典{@code LockDeviceDic}中获取
         */
        private Integer device_code;
    }
}
