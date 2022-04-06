package com.volunteer.protocol.service2Lock.Operation;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 23:36
 * Description: 锁机操作
 */
@Data
@AllArgsConstructor
public class LockOperation implements Operation{
    /**
     * 锁机开关 true开
     */
    private boolean lock_switch;
    /**
     * 超时关锁 单位毫秒  0为取消这个功能，无限等待，禁止设置！！！
     */
    private Integer auto_lock_timeout;
}
