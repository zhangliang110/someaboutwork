/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeServer.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月20日
 */
package org.demo.netty.ch3;


import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/** 
 * 第一个用netty 实现的TimeServer
 * 
 * <p>
 * <a href="TimeServer.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TimeServer {
    public void bind(int port) throws Exception {
        //配置服务端的NIO线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();  //这个线程组用来接口客户端的请求并连接
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();    //这个线程组用来读写信息
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChildChannelHandler());
            //绑定端口,同步等待成功
            ChannelFuture future = boot.bind(new InetSocketAddress("127.0.0.1", port)).sync();
            
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            //推出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    
    
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new TimeServerHandler());
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
        new TimeServer().bind(port);
    }
}
