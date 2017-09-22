/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)EchoServer.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月21日
 */
package org.demo.netty.ch7;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/** 
 * 功能描述
 * 
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
                        //针对出现的TCP粘包/粘包问题进行解决。解决方案为：使用netty 提供的LengthFieldPrepender 和 lengthFieldBaseFramedDecoder
                        ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                        ch.pipeline().addLast("frame encoder", new LengthFieldPrepender(2));    //对pack前添加 2字节的 frame header 标志数据长度
                        
                        ch.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
                        ch.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
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
