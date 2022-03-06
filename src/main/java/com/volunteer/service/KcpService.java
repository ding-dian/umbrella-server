package com.volunteer.service;

import cn.hutool.core.util.ObjectUtil;
import com.volunteer.entity.common.UmbrellaDic;
import com.volunteer.util.SendMailUtil;
import com.volunteer.util.SpringContextUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import kcp.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

/**
 * @author: 梁峰源
 * @date: 2022/2/3 19:57
 * 由于学校里校园网登录需要账号，爱心雨伞锁机如果断线重连比较麻烦，维护成本高，用UDP发包校园网有几个端口不会认证，直接外发
 * 所以爱心雨伞锁机需要基于UDP协议的kcp协议进行联网，kcp协议具有TCP协议的可靠性和UDP协议的低延迟的特点
 * <a href="https://github.com/l42111996/java-Kcp.git">KCP协议实现地址</a>
 * 整体的思路如下：
 * 1、用户在微信端借伞，UmbrellaController调用KcpService的sendUnlock2Lock方法发送借伞信号
 * 2、用户需要在规定时间里面将雨伞借走
 * 3、锁机发送关锁成功/失败信号，服务器进行判断
 */
@Slf4j
@Component
public class KcpService implements KcpListener {

    @Autowired
    private SendMailUtil sendMailUtil;
    @Value("${umbrella.defaultUnlockWaitTime}")
    private int defaultUnlockWaitTime;//默认锁机开锁等待时间
    @Value("${Kcp.port}")
    private Integer kcpPort;
    private Ukcp ukcp;
    //volatile保证线程可见性
    private volatile boolean unlockFlag;//用来判断锁机是否开启
    private volatile boolean lockFlag;//用来判断锁机是否关闭
    private boolean isConnect;//判断客户端是否掉线
    private Instant firstLowPowerInstant;

    public Ukcp getUkcp() {
        return ukcp;
    }


//    @Scheduled(fixedRate=10000)     //每间隔10秒执行一次, 检查是否掉线, 是就让它重新连接
//    @PostConstruct
//    void configureTasks() {
//        if (!isConnect){
//            try {
//                connect();
//            } catch (Exception e) {
//                log.error(e.getMessage());
//            }
//        }
//    }

    /**
     * 初始化服务器端
     */
    public void initKcpService() {
        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.nodelay(true, 40, 2, true);
        channelConfig.setSndwnd(512);
        channelConfig.setRcvwnd(512);
        channelConfig.setMtu(512);
        channelConfig.setAckNoDelay(false);
//        channelConfig.setTimeoutMillis(10000);
        channelConfig.setUseConvChannel(true);
        channelConfig.setCrc32Check(false);
        //启动Kcp程序，注意KcpServer和KcpService，左边那个是系统提供的，右边那个是我们自己写的
        KcpServer kcpServer = new KcpServer();
        kcpServer.init(this, channelConfig, kcpPort);
        log.info("服务器启动");
        isConnect = true;
    }

    /**
     * 发送开锁信号给锁机
     */
    public String sendUnlockMsg2Lock() {
        long start = System.currentTimeMillis();
        long waitTime = 1000 * defaultUnlockWaitTime;
        if (ObjectUtil.isNull(ukcp)) {
            throw new RuntimeException("锁机掉线了，发送消息失败");
        }
        //发送开锁信号
        boolean isSend = ukcp.write(UmbrellaDic.UNLOCK_FRAME);
        if (!isSend) {
            return UmbrellaDic.UNLOCK_FAIL_MSG;
        }
        //线程阻塞十秒判断开锁状态
        while (true) {
            //在handleReceive方法中改变flag的值，必须要完成开锁后关锁
            if (unlockFlag && lockFlag) {
                return UmbrellaDic.UNLOCK_SUCCESS_MSG;
            } else if (System.currentTimeMillis() - start > waitTime) {
                return UmbrellaDic.UNLOCK_OVERTIME_MSG;
            }
        }
    }

    /**
     * 有连接上来了
     */
    @Override
    public void onConnected(Ukcp ukcp) {
        //锁机上线，发送邮件给管理员
        sendMailUtil.sendLockMsg2Admin("锁机连接成功连接成功");
        log.info("new Connection: " + Thread.currentThread().getName() + ukcp.user().getRemoteAddress());
        this.ukcp = ukcp;
    }

    /**
     * 处理接受到的KCP请求
     *
     * @param buf  Netty提供的缓冲区实现：ByteBuf
     * @param ukcp kcp连接对象
     */
    @Override
    public void handleReceive(ByteBuf buf, Ukcp ukcp) {
        if (ObjectUtil.isNull(this.ukcp)) {
            this.ukcp = ukcp;
        }
        short curCount = buf.getShort(buf.readerIndex());
        String str;
        if (buf.hasArray()) {
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        } else {
            int length = buf.readableBytes();
            byte[] array = new byte[length];
            buf.getBytes(buf.readerIndex(), array);
            str = new String(array);
        }
        log.info(Thread.currentThread().getName() + "服务器收到锁机信息recv: " + str);
        //处理帧，
        if (UmbrellaDic.UNLOCK_SUCCESS_FRAME.equalsIgnoreCase(str)) {
            lockFlag = false;
            unlockFlag = true;//开锁成功信号
        } else if (unlockFlag && UmbrellaDic.LOCK_SUCCESS_FRAME.equalsIgnoreCase(str)) {
            lockFlag = true;//关锁成功信号
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //堵塞线程的标识
            unlockFlag = false;
        } else if (UmbrellaDic.UNLOCK_FAIL_FRAME.equals(str)) {
            log.error("开锁失败锁机异常！");
            sendMailUtil.sendLockMsg2Admin("开锁失败锁机异常！请赶快处理");
        } else if (UmbrellaDic.LOCK_FAIL_FRAME.equalsIgnoreCase(str)) {
            log.error("关锁失败");
        } else if (UmbrellaDic.LOCK_LOW_POWER_FRAME.equals(str)) {
            //电量不足，给管理员发邮件，注意此帧会不断发，因为锁机马上将失连，情况危急
            log.error("锁机没电了");
            //这里需要服务器每隔一定的时间提醒管理员
            if (ObjectUtil.isNull(firstLowPowerInstant)) {
                firstLowPowerInstant = Instant.now();
                //先发送一次
                sendMailUtil.sendLockMsg2Admin("锁机没电了！请赶快处理");
            } else if (Duration.between(firstLowPowerInstant, Instant.now()).toMinutes() > 5) {
                //归零
                firstLowPowerInstant = null;
                sendMailUtil.sendLockMsg2Admin("锁机没电了！已经过了五分钟了请赶快处理");
            }
        }

        //连接线测试的帧
        if (UmbrellaDic.PING_CLIENT_FRAME.equalsIgnoreCase(str)) {
            //连通性测试，返回服务器的测试帧
            this.ukcp.write(UmbrellaDic.PING_SERVICE_FRAME);
        }
        if (curCount == -1) {
            ukcp.close();
        }
    }

    /**
     * 异常处理
     */
    @Override
    public void handleException(Throwable throwable, Ukcp ukcp) {
        //异常，通知管理员"
        log.error("爱心雨伞锁机连接异常，请赶快处理，{}", throwable.getMessage());
        sendMailUtil.sendLockMsg2Admin("爱心雨伞锁机连接异常，请赶快处理\n" + throwable.getMessage());
    }

    /**
     * 连接关闭
     */
    @Override
    public void handleClose(Ukcp ukcp) {
        //异常，通知管理员"
        log.error("锁机掉线了");
        sendMailUtil.sendLockMsg2Admin("锁机掉线了");
        isConnect = false;
    }

    /**
     * 该对象销毁时，销毁连接
     */
    @PreDestroy
    public void destroyComponent() {
        disConnect();
    }

    private void disConnect() {
        if (ObjectUtil.isNotNull(ukcp) && isConnect) {
            ukcp.close();
        }
    }
}
