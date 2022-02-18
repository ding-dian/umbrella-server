package com.volunteer.service;

import cn.hutool.core.util.ObjectUtil;
import com.volunteer.entity.common.UmbrellaDic;
import com.volunteer.util.SendMailUtil;
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
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author: 梁峰源
 * @date: 2022/2/3 19:57
 * 由于学校里校园网登录需要账号，爱心雨伞锁机如果断线重连比较麻烦，维护成本高，用UDP发包校园网有几个端口不会认证，直接外发
 * 所以爱心雨伞锁机需要基于UDP协议的kcp协议进行联网，kcp协议具有TCP协议的可靠性和UDP协议的低延迟的特点
 * <a href="https://github.com/l42111996/java-Kcp.git">KCP协议实现地址</a>
 * 整体的思路如下：
 *      1、用户在微信端借伞，UmbrellaController调用KcpService的sendUnlock2Lock方法发送借伞信号
 *      2、
 */
@Slf4j
@Component
//@EnableScheduling//开启定时任务
public class KcpService implements KcpListener {

    @Resource
    private SendMailUtil sendMailUtil;
    @Value("${umbrella.defaultUnlockWaitTime}")
    private static int defaultUnlockWaitTime;//默认锁机开锁等待时间
    private static Ukcp ukcp;
    private static volatile boolean flag;//用来判断锁机是否开启并且用户借走了雨伞
    private static boolean isConnect;//判断服务器是否掉线
    public Ukcp getUkcp() {
        return ukcp;
    }
    public boolean getFlag(){return this.flag;}


//    @Scheduled(fixedRate=10000)     //每间隔10秒执行一次, 检查是否掉线, 是就让它重新连接
//    @PostConstruct
//    @Async
    void configureTasks() {
        connect();
//        if (!isConnect){
//            try {
//                connect();
//            } catch (Exception e) {
//                log.error(e.getMessage());
//            }
//        }
    }

    public static void connect(){
        KcpService myServer = new KcpService();
        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.nodelay(true,40,2,true);
        channelConfig.setSndwnd(512);
        channelConfig.setRcvwnd(512);
        channelConfig.setMtu(512);
        channelConfig.setAckNoDelay(false);
//        channelConfig.setTimeoutMillis(10000);
        channelConfig.setUseConvChannel(true);
        channelConfig.setCrc32Check(false);
        //启动Kcp程序
        KcpServer kcpServer = new KcpServer();
        kcpServer.init(myServer,channelConfig,10086);
        log.info("服务器启动");
        isConnect = true;
    }

    /**
     * 发送开锁信号给锁机
     */
    public static String sendUnlockMsg2Lock(){
        long start = System.currentTimeMillis();
        long waitTime = 1000*defaultUnlockWaitTime;
        if(ObjectUtil.isNull(ukcp)){
            throw new RuntimeException("锁机掉线了，发送消息失败");
        }
        //发送开锁信号
        boolean isSend = ukcp.write(UmbrellaDic.UNLOCK_FRAME);
        if(!isSend){
            return UmbrellaDic.UNLOCK_FAIL_MSG;
        }
        //线程阻塞十秒判断开锁状态
        while (true){
            //在handleReceive方法中改变flag的值
            if(flag){
                return UmbrellaDic.UNLOCK_SUCCESS_MSG;
            }else if(System.currentTimeMillis() - start > waitTime){
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
//        sendMailUtil.send2Admin("锁机连接成功连接成功");
        log.info("new Connection: " + Thread.currentThread().getName() + ukcp.user().getRemoteAddress());
        KcpService.ukcp = ukcp;
    }

    /**
     * 处理接受到的KCP请求
     * @param buf Netty提供的缓冲区实现：ByteBuf
     * @param ukcp kcp连接对象
     */
    @Override
    public void handleReceive(ByteBuf buf, Ukcp ukcp) {
        if(ObjectUtil.isNull(KcpService.ukcp)){
            KcpService.ukcp = ukcp;
        }
        flag = false;
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
        if(UmbrellaDic.UNLOCK_SUCCESS_FRAME.equalsIgnoreCase(str)){
            flag = true;//开锁
        }
        if(UmbrellaDic.PING_FRAME.equalsIgnoreCase(str)){
            //连通性测试，直接返回
            KcpService.ukcp.write(buf);
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
//        sendMailUtil.send2Admin("爱心雨伞锁机连接异常，请赶快处理\n"+throwable.getMessage());
        log.error("爱心雨伞锁机连接异常，请赶快处理，{}",throwable.getMessage());
    }

    /**
     * 连接关闭
     */
    @Override
    public void handleClose(Ukcp ukcp) {
        //异常，通知管理员"
//        sendMailUtil.send2Admin("锁机掉线了");
        log.error("锁机掉线了");
    }
}
