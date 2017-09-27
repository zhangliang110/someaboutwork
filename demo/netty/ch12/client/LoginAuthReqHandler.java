/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)LoginAuthReqHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月25日
 */
package org.demo.netty.ch12.client;

import org.demo.netty.ch12.MessageType;
import org.demo.netty.ch12.struct.Header;
import org.demo.netty.ch12.struct.NettyMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/** 
 * 握手的发起是在客户端和服务端TCP链路建立成功通道激活时，握手消息的接入和安全认证在服务端处理。
 * 
 * <p>
 * <a href="LoginAuthReqHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class LoginAuthReqHandler extends ChannelHandlerAdapter {
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        try {
            ctx.writeAndFlush(this.buildLoginReq());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        
        //如果是握手应答消息,需要判断是否认证成功
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            byte loginResult = (byte)message.getBody();
            if(loginResult != (byte) 0) {
                //握手失败，关闭连接
                ctx.close();
            } else {
                System.out.println("Login is ok : " + message);
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }
    
    private NettyMessage buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
