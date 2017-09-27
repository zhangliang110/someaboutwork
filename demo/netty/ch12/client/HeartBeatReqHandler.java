/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)HeartBeatReqHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月25日
 */
package org.demo.netty.ch12.client;

import java.util.concurrent.TimeUnit;

import org.demo.netty.ch12.MessageType;
import org.demo.netty.ch12.struct.Header;
import org.demo.netty.ch12.struct.NettyMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

/** 
 * 
 * <p>
 * <a href="HeartBeatReqHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class HeartBeatReqHandler extends ChannelHandlerAdapter {
    private volatile ScheduledFuture<?> heartBeat;
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        
        //握手成功，发送心跳信息
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
        } else if (message.getHeader() != null  && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()) {
            System.out.println("client receive the heartbeat message ----------->" + message);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (heartBeat != null) {
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }
    
    private class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;
        
        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }
        
        @Override
        public void run() {
            NettyMessage heartBeat = this.buildHeartBeat();
            System.out.println("Client send the heartbeat message ------>" + heartBeat);
            ctx.writeAndFlush(heartBeat);
        }
        
        private NettyMessage buildHeartBeat() {
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            message.setHeader(header);
            return message;
        }
    }
}

