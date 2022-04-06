package com.volunteer.protocol.lock2Service;

import lombok.Data;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 20:07
 * Description: 锁机上报状态
 */
@Data
public class LockStatus {
    private String cpu_freq;	//	CPU频率
    private String sdk_version;	//	SDK版本号
    private String free_heap;	//	剩余堆内存
    private String work_time;	//	系统已运行时间
    private String chip_id;	//	MCU id
}
