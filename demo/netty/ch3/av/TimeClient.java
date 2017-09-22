/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeClient.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月20日
 */
package org.demo.netty.ch3.av;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/** 
 * 支持TCP粘包的TimeClient
 * <p>
 * <a href="TimeClient.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TimeClient {
    public void connect(String host, int port) throws Exception{
        //配置客户端线程组
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap boot = new Bootstrap();
            boot.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new TimeClientHandler());
                    }
                    
                });
            
            //发起异步连接操作
            ChannelFuture f = boot.connect(new InetSocketAddress(host, port)).sync();
            
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            //推出释放 group线程组的资源
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
        new TimeClient().connect("127.0.0.1", port);
    }
}
