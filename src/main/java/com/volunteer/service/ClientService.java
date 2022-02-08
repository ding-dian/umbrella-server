package com.volunteer.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *  <p>
 * 与爱心雨伞终端通讯实现类，基于TCP协议
 * </p>
 *
 * @author: 梁峰源
 * @date: 2021/10/17 17:16
 */
//@Service
//@EnableScheduling//开启定时任务
@Slf4j
public class ClientService {

    //与服务器通信的异步Channel
    private AsynchronousSocketChannel channel;
    private final ExecutorService executor = Executors.newFixedThreadPool(20);
    private final String address = "192.168.1.200";//"192.168.1.200";127.0.0.1
    private final String port = "6005";//6004~6008

//    @Autowired//将后端数据推送到前端
//    private SimpMessagingTemplate simpMessagingTemplate;


    /***
     * socket连接
     * ***/
    public void connect() throws Exception {
        ByteBuffer buff = ByteBuffer.allocate(1024);
        ExecutorService pool = Executors.newFixedThreadPool(5);
        //以指定线程池来创建一一个AsynchronousChannelGroup
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(pool);
        //以channelGroup作为组管理器来创建AsynchronousSocketChannel
        channel = AsynchronousSocketChannel.open(channelGroup);
        channel.connect(new InetSocketAddress(address, Integer.parseInt(port))).get(3, TimeUnit.SECONDS);
        buff.clear();
        channel.read(buff, null, new CompletionHandler<Integer, Object>() {
            private int countNull;      //记录传递过来的数据是空的次数
            private long currentTime = System.currentTimeMillis();

            @Override
            public void completed(Integer result, Object attachment) {
                buff.flip();
                String content = StandardCharsets.UTF_8.decode(buff).toString().trim();
                maxCount(content);
                //收到的数据帧，开始业务代码的书写
                log.info("==收到的帧=="+content);
                if(content.matches("Hwdhe0109VID.{6}T")){
                }
                buff.clear();
                try {
                    channel.read(buff, null, this);
                } catch (NullPointerException n) {
                    log.error(n.getMessage());
                }
            }
            //假设服务器掉线情况，就会发送无数垃圾数据过来，导致程序卡顿，这里检测发送过来的短时间内的次数，超过十次就让它断开连接
            private void maxCount(String content) {
                if ("".equals(content)) {
                    if (System.currentTimeMillis() - currentTime < 500) {
                        countNull++;
                    } else {
                        countNull = 0;
                    }
                    currentTime = System.currentTimeMillis();
                    if (countNull > 10) {
                        try {
                            disConnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        log.error("超过十次收到空数据,就退出");
                    }
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("控制台连接退出");
            }
        });
    }

    @Scheduled(fixedRate=10000)     //每间隔10秒执行一次, 检查是否掉线, 是就让它重新连接至服务器
    private void configureTasks() {
        if (channel == null || !channel.isOpen()){
            try {
                connect();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 关闭连接
     */
    public void disConnect() throws IOException {
        if (this.channel != null && channel.isOpen()) {
            channel.shutdownInput();
            channel.shutdownOutput();
            channel.close();
            channel = null;
        }
    }

    /**
     * 该对象销毁（Build）时，销毁与服务器连接
     */
    @PreDestroy
    public void destroyComponent() throws IOException {
        disConnect();
    }

    public AsynchronousSocketChannel getChannel() {
        return channel;
    }
}
