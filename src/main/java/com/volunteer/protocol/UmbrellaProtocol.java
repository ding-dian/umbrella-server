package com.volunteer.protocol;


import com.volunteer.protocol.service2Lock.ConfigFilter;
import com.volunteer.protocol.service2Lock.Operation.LockOperation;
import com.volunteer.protocol.service2Lock.Service2LockVo;
import com.volunteer.protocol.service2Lock.ServiceControlOperation;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @author: 梁峰源
 * @date: 2022/4/5 19:38
 * @Description: 对协议进行封装
 */
public class UmbrellaProtocol {
    /**
     * 发送开锁的帧
     */
    public static ByteBuf sendUnlockMsg(int id, int autoLockTimeOut) {
        Service2LockVo service2LockVo2 = new Service2LockVo()
                .setId(id)
                .setPost_type(LockDeviceDic.PostType.control)
                .setContent(new ServiceControlOperation(
                        new ServiceControlOperation.Target(LockDeviceDic.EL_LOCK, LockDeviceDic.EL_LOCK_CODE),
                        new LockOperation(true, autoLockTimeOut)
                ))
                .setTimestamp(String.valueOf(System.currentTimeMillis()));
        return Unpooled.copiedBuffer(String.valueOf(ConfigFilter.filter(service2LockVo2)), StandardCharsets.UTF_8);
    }

    /**
     * 发送关锁的帧
     */
    public static ByteBuf sendLockMsg(int id, int autoLockTimeOut) {
        Service2LockVo service2LockVo2 = new Service2LockVo()
                .setId(id)
                .setPost_type(LockDeviceDic.PostType.control)
                .setContent(new ServiceControlOperation(
                        new ServiceControlOperation.Target(LockDeviceDic.EL_LOCK, LockDeviceDic.EL_LOCK_CODE),
                        new LockOperation(false, autoLockTimeOut)
                ))
                .setTimestamp(String.valueOf(System.currentTimeMillis()));
        return Unpooled.copiedBuffer(String.valueOf(ConfigFilter.filter(service2LockVo2)), StandardCharsets.UTF_8);
    }
}
