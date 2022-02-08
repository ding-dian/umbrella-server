package com.volunteer.service;

import io.netty.buffer.ByteBuf;
import kcp.ChannelConfig;
import kcp.KcpListener;
import kcp.KcpServer;
import kcp.Ukcp;

/**
 * @author: 梁峰源
 * @date: 2022/2/3 19:57
 * 由于学校里校园网登录需要账号，爱心雨伞锁机如果断线重连比较麻烦，维护成本高，用UDP发包校园网有几个端口不会认证，直接外发
 * 所以爱心雨伞锁机需要基于UDP协议的kcp协议进行联网，kcp协议具有TCP协议的可靠性和UDP协议的低延迟的特点
 * <a href="https://github.com/l42111996/java-Kcp.git">KCP协议实现地址</a>
 */
public class KcpService implements KcpListener {

    /**
     * 初始化
     */
    public void init(){
        KcpService kcpIdleExampleServer = new KcpService();
        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.nodelay(true,40,2,true);
        channelConfig.setSndwnd(1024);
        channelConfig.setRcvwnd(1024);
        channelConfig.setMtu(1400);
        //channelConfig.setFecDataShardCount(10);
        //channelConfig.setFecParityShardCount(3);
        channelConfig.setAckNoDelay(false);
        channelConfig.setCrc32Check(true);
        //channelConfig.setTimeoutMillis(10000);
        KcpServer kcpServer = new KcpServer();
        kcpServer.init(kcpIdleExampleServer, channelConfig, 10020);
    }

    /**
     * socket连接
     */
    @Override
    public void onConnected(Ukcp ukcp) {

    }

    /**
     * 处理接受到的KCP请求
     * @param byteBuf Netty提供了自己的缓冲区实现ByteBuf
     * @param ukcp kcp连接对象
     */
    @Override
    public void handleReceive(ByteBuf byteBuf, Ukcp ukcp) {
        byte[] bytes = new  byte[byteBuf.readableBytes()];
        byteBuf.getBytes(byteBuf.readerIndex(),bytes);
        System.out.println("收到消息: "+new String(bytes));
    }

    /**
     * 异常处理
     */
    @Override
    public void handleException(Throwable throwable, Ukcp ukcp) {

    }

    /**
     * 连接关闭
     */
    @Override
    public void handleClose(Ukcp ukcp) {

    }
}
