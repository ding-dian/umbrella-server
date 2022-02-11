package com.volunteer.service;


import io.netty.buffer.ByteBuf;
import kcp.KcpListener;
import kcp.Ukcp;

/**
 * @author: 梁峰源
 * @date: 2022/2/3 20:05
 *
 */
public class KcpClient implements KcpListener {
    @Override
    public void onConnected(Ukcp ukcp) {

    }

    @Override
    public void handleReceive(ByteBuf byteBuf, Ukcp ukcp) {

    }

    @Override
    public void handleException(Throwable throwable, Ukcp ukcp) {

    }

    @Override
    public void handleClose(Ukcp ukcp) {

    }
}

