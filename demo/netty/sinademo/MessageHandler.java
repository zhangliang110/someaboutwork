/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MessageHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月31日
 */
package org.demo.netty.sinademo;

import org.demo.netty.sinademo.codec.SinaChatMsg;
import org.demo.netty.sinademo.util.LoginCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息请求处理类
 * 
 * <p>
 * <a href="MessageHandler.java"><i>View Source</i></a>
 * </p>
 * 
 * @author zl
 * @version 3.0
 * @since 1.0
 */
public class MessageHandler extends ChannelHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginCacheUtils.addUserCache(ctx.channel());
        super.channelActive(ctx);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SinaChatMsg chatmsg = (SinaChatMsg)msg;
        if (chatmsg.getMsgType() == 1) {
            //登陆
            LoginCacheUtils.addUserMap(chatmsg.getFrname(), ctx.channel());
            LOGGER.info("{}登录成功",chatmsg.getFrname());
        } else if (!LoginCacheUtils.isLogin(ctx.channel())) {
            throw new Exception("未登录");
        }
        LOGGER.info(chatmsg.getFrname() + "say：" + chatmsg.getContent());
        //群发
        LoginCacheUtils.userMap.forEach((ch, name) -> {
            ch.writeAndFlush(chatmsg);
        });
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LoginCacheUtils.removeUserChannel(ctx.channel());
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LoginCacheUtils.removeUserChannel(ctx.channel());
        LOGGER.info("发生了异常，用户被迫退出");
        super.exceptionCaught(ctx, cause);
    }
    
}
