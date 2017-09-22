/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)EchoServer.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月20日
 */
package org.demo.netty.ch5;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/** 
 * 
 * 使用了DelimiterBasedFrameDecoder作为编码器
 * 
 * 还有一个简单的FixedLengthFrameDecoder用于对固定长度的消息进行自动解码， 有了这些解码器就可以轻松地完成很多消息的自动解码，
 * 而且不需要考虑TCP粘包/拆包的问题。
 * <p>
 * <a href="EchoServer.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class EchoServer {
    public void bind(String host, int port) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    protected void initChannel(SocketChannel ch) throws Exception {
                        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new EchoServerHandler());
                    }
                });
            
            //绑定地址，同步等待成功消息
            ChannelFuture f = boot.bind(new InetSocketAddress(host, port)).sync();
            
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
            
        } finally {
            //释放资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        int port = 8088;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //使用默认端口配置
                e.printStackTrace();
            }
        }
        new EchoServer().bind("127.0.0.1", port);
    }
}
