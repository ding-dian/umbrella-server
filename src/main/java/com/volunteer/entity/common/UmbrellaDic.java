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
     * 锁机电量不足帧
     */
    public static final String LOCK_LOW_POWER_FRAME = "Hwclowpower00T";

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
    public static final String UNLOCK_SUCCESS_FRAME = "HwcUnLock07successT";

    /**
     * 锁机返回开锁失败帧
     */
    public static final String UNLOCK_FAIL_FRAME = "HwcUnLock04failT";

    /**
     * 锁机返回关锁成功帧
     */
    public static final String LOCK_SUCCESS_FRAME = "HwcLock07successT";

    /**
     * 锁机返回关锁失败帧
     */
    public static final String LOCK_FAIL_FRAME = "HwcLock04failT";

    /**
     * 和锁机ping连通性测试帧，客户端发给服务器
     */
    public static final String PING_CLIENT_FRAME = "Hwcping00T";

    /**
     * 开锁帧，服务器发给锁机
     */
    public static final ByteBuf UNLOCK_FRAME = Unpooled.copiedBuffer("HwsLock02onT", StandardCharsets.UTF_8);

    /**
     * 和锁机ping连通性测试帧，服务端发给客户端
     */
    public static final ByteBuf PING_SERVICE_FRAME = Unpooled.copiedBuffer("Hwsping00T", StandardCharsets.UTF_8);


    /**
     * 用户已经借取过雨伞了
     */
    public static final String UMBRELLA_BORROWED_MSG = "umbrella_borrowed";

    /**
     * 用户借伞成功
     */
    public static final String UMBRELLA_BORROW_SUCCESS_MSG = "UMBRELLA_BORROW_SUCCESS_MSG";

    /**
     * 用户借伞失败
     */
    public static final String UMBRELLA_BORROW_FAIL_MSG = "UMBRELLA_BORROW_FAIL_MSG";

    /**
     * 用户没有没有借伞记录
     */
    public static final String UMBRELLA_RETURN_NOT_BORROW_MSG = "UMBRELLA_RETURN_NOT_BORROW_MSG";

    /**
     * 用户还伞成功
     */
    public static final String UMBRELLA_RETURN_SUCCESS_MSG = "UMBRELLA_RETURN_SUCCESS_MSG";

    /**
     * 用户还伞失败
     */
    public static final String UMBRELLA_RETURN_FAIL_MSG = "UMBRELLA_RETURN_FAIL_MSG";


}
