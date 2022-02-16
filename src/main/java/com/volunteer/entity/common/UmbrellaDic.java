package com.volunteer.entity.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @author: 梁峰源
 * @date: 2022/2/15 23:01
 * Description:
 */
public class UmbrellaDic {

    /**
     * 锁机开启失败
     */
    public static final String UNLOCK_FAIL_MSG = "UNLOCK_FAIL_MSG";

    /**
     * 锁机开启成功
     */
    public static final String UNLOCK_SUCCESS_MSG = "UNLOCK_SUCCESS_MSG";

    /**
     * 锁机开启超时
     */
    public static final String UNLOCK_OVERTIME_MSG = "UNLOCK_OVERTIME_MSG";

    /**
     * 锁机返回开锁成功帧
     */
    public static final String UNLOCK_SUCCESS_FRAME = "HwcLock07successT";

    /**
     * 和锁机ping连通性测试帧
     */
    public static final String PING_FRAME = "Hwcping00T";

    /**
     * 开锁帧，服务器发给锁机
     */
    public static final ByteBuf UNLOCK_FRAME = Unpooled.copiedBuffer("HwsLock02onT", StandardCharsets.UTF_8);

}
