package com.volunteer.protocol.lock2Service;

import lombok.Data;

/**
 * @author: 梁峰源
 * @date: 2022/3/28 10:15
 * Description: 与锁机通信帧
 */
@Data
public class Lock2ServiceVo {
    private Integer id;		//设备唯一编号
    private String post_type;		//上报消息类型 status、error、return、boot开机
    private Object status;		//设备状态信息
    private String timestamp;		//发送时间戳 13位
}
