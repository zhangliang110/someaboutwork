/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)EchoClient.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月21日
 */
package org.demo.netty.ch5;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/** 
 * 使用DelimiterBasedFrameDecoder客户端开发
 * 
 * <p>
 * <a href="EchoClient.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class EchoClient {
    
    public void connect(int port, String host) throws Exception {
        //配置客户端NIO线程组
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap boot = new Bootstrap();
            boot.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                });
            //连接服务器并同步等待结果
            ChannelFuture f = boot.connect(new InetSocketAddress(host, port)).sync();
            
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            //退出，并释放group资源
            group.shutdownGracefully();
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
        
        new EchoClient().connect(port, "127.0.0.1");
    }
}
