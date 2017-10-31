/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ClientHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月31日
 */
package org.demo.netty.sinademo;

import org.demo.netty.sinademo.codec.SinaChatMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/** 
 * 
 * <p>
 * <a href="ClientHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class ClientHandler extends ChannelHandlerAdapter{
    private ChannelHandlerContext ctx;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SinaChatMsg chatMsg = (SinaChatMsg)msg;
        LOGGER.info(chatMsg.getFrname() + "say:" + chatMsg.getContent());
        super.channelRead(ctx, msg);
    }
    
    public void register() {
        SinaChatMsg chatMsg = new SinaChatMsg();
        chatMsg.setFrname("zl");
        chatMsg.setMsgType(1);  //登录
        chatMsg.setContent("zhuce");
        ctx.channel().writeAndFlush(chatMsg);
    }
    
    public void sendMessage(String message, String name) throws Exception{
        if (ctx == null) {
            throw new Exception("请先登录");
        }
        SinaChatMsg chatMsg = new SinaChatMsg();
        chatMsg.setCt(1);
        chatMsg.setFrname(name);
        chatMsg.setContent(message);
        ctx.channel().writeAndFlush(chatMsg);
    }
}
