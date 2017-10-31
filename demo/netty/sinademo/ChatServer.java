/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ChatServer.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月31日
 */
package org.demo.netty.sinademo;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.demo.netty.sinademo.codec.MsgpackDecoder;
import org.demo.netty.sinademo.codec.MsgpackEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/** 
 * 用netty去实现一个聊天软件
 * 
 * <p>
 * <a href="ChatServer.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class ChatServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServer.class);
    
    public void doStart(final String host, final int port) throws Exception {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBoot = new ServerBootstrap();
            serverBoot.group(boss, worker).option(ChannelOption.SO_BACKLOG, 1024).channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
//                                ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65536, 0, 2, 0, 2));
                                ch.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
                                ch.pipeline().addLast("msgpack ecoder", new MsgpackEncoder());
                                ch.pipeline().addLast("msg handler", new MessageHandler());
                            }
                        });
            //绑定端口并同步等待消息
            ChannelFuture f = serverBoot.bind(new InetSocketAddress(host, port)).sync();
            LOGGER.info("服务启动成功，地址:{},端口:{}", host, port);
            f.channel().closeFuture().sync();
        } finally {
            //释放线程组
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        new ChatServer().doStart("localhost", 8088);
    }
}
