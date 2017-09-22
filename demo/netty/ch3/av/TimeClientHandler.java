/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeClientHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月20日
 */
package org.demo.netty.ch3.av;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/** 
 * 支持TCP粘包的handler
 * <p>
 * <a href="TimeClientHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TimeClientHandler extends ChannelHandlerAdapter{
    private static final Logger logger = Logger.getLogger(TimeClientHandler.class);
    
    private int counter;
    
    private byte[] req;
    
    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.lineSeparator()).getBytes();
    }
    
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.copiedBuffer(req);
            ctx.writeAndFlush(message);
        }
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String)msg;
        System.out.println("The client get NOW is" + message + "the counter is " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("the exception was occurred");
        ctx.close();
    }
}
