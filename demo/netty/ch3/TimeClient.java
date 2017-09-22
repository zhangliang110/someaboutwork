/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeClient.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月20日
 */
package org.demo.netty.ch3;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 第一个用netty开发的TimeClient
 * 
 * <p>
 * <a href="TimeClient.java"><i>View Source</i></a>
 * </p>
 * 
 * @author zl
 * @version 3.0
 * @since 1.0
 */
public class TimeClient {
    public void connect(int port, String host) throws Exception {
        // 配置客户端NIO线程组
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new org.demo.netty.ch3.TimeClientHandler());
                }
            });
           
            //发起异步连接操作，然后同步等待成功
            ChannelFuture future = b.connect(host, port).sync();
            
            //等待客户端链路关闭
            future.channel().closeFuture().sync();
        } finally {
            //推出并释放NIO线程组资源
            group.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        int port = 8088;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //使用默认配置
            }
        }
       
        new TimeClient().connect(port, "127.0.0.1");
    }
}
