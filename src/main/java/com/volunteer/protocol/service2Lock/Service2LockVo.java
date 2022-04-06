package com.volunteer.protocol.service2Lock;

import com.volunteer.protocol.LockDeviceDic;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: 梁峰源
 * @date: 2022/3/28 10:15
 * Description: 与锁机通信帧
 */
@Data
@Accessors(chain = true)
public class Service2LockVo {
    /**
     * 控制器设备的唯一编号，请根据字典{@code LockDeviceDic}中获取
     * <p>
     * ex:如果你想输入led灯的编号，你可以输入
     * <pre>
     *      LockDeviceDic.led
     * </pre>
     */
    private Integer id;
    /**
     * 下发消息类型,共有以下几种类型可选
     * <ul>
     * <li>test 表示执行ping操作或者其他测试操作
     * <li>control 表示要发送控制帧，对应对象均实现了{@code Operation}接口
     * <li>config 表示要发送配置帧，对应对象均实现了{@code Config}接口
     * </ul>
     */
    private LockDeviceDic.PostType post_type;
    /**
     * 根据post_type决定
     */
    private Object content;
    /**
     * 发送时间戳 13位
     */
    private String timestamp;
}
