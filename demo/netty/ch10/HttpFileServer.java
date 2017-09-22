/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)HttpFileServer.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月21日
 */
package org.demo.netty.ch10;


import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/** 
 * 
 * 用netty 实现基于http协议的 文件服务器
 * <p>
 * <a href="HttpFileServer.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class HttpFileServer {
    private static final String DEFAULT_PATH = "/src/main/java/org/demo/netty/";
    
    public void run(final String host, final int port) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                        ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));  //将多个http消息合并
                        ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());   //便于大文件的写入，而且内存占用少
                        ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(DEFAULT_PATH));
                    }
                    
                });
            //绑定端口并同步等待成功消息
            ChannelFuture f = boot.bind(new InetSocketAddress(host, port)).sync();
            
            System.out.println("Http 服务启动成功后， 路劲为" + DEFAULT_PATH);
            
            f.channel().closeFuture().sync();   //等待服务端口关闭
        } finally {
            //释放 线程组资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("user.dir"));
        new HttpFileServer().run("127.0.0.1", 8088);
    }
    
}
