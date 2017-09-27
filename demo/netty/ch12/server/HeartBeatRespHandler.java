/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)HeartBeatRespHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月25日
 */
package org.demo.netty.ch12.server;

import org.demo.netty.ch12.MessageType;
import org.demo.netty.ch12.struct.Header;
import org.demo.netty.ch12.struct.NettyMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/** 
 * 
 * <p>
 * <a href="HeartBeatRespHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class HeartBeatRespHandler extends ChannelHandlerAdapter {
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        
        //返回心跳应答消息
        if (message.getHeader() != null  && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()) {
            System.out.println("Receive client heart beat message -------->" + message);
            NettyMessage heartBeat = this.buildHeartBeat();
            System.out.println("server send the hearbeat message --------->" + heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    /**
     * 构造心跳应答消息
     * @return
    */
    private NettyMessage buildHeartBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
