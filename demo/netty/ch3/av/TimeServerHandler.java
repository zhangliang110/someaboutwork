/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeServerHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月20日
 */
package org.demo.netty.ch3.av;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/** 
 * 第一个netty写的 TimeServer的handler 启用了netty的 解码器  LineBasedFrameDecoder 和 StringDecoder
 * 
 * 
 * LineBasedFrameDecoder的工作原理是： 依次遍历ByteBuf中的可读字节，判断看是否有"\n"/ "\r\n"。如果有以此为结束位置，将之前的索引位置到结束位置组成一行。
 * 同时也支持匹配最大的单行长度，超过此长度之后还没发现换行符则抛出异常。
 * <p>
 * <a href="TimeServerHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TimeServerHandler extends ChannelHandlerAdapter{

    private int counter;
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String)msg;
        System.out.println("The time server receive order:" + body + "the counter is" + ++counter);
        String currentTime = "QUERY TIME ORDER".equals(body) ? new Date(System.currentTimeMillis()).toString() : "BAD order";
        currentTime += System.lineSeparator();
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
