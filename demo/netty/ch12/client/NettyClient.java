/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)NettyClient.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月25日
 */
package org.demo.netty.ch12.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.demo.netty.ch12.NettyConstant;
import org.demo.netty.ch12.NettyMessageDecoder;
import org.demo.netty.ch12.NettyMessageEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * 增加断线重连接操作
 * <p>
 * <a href="NettyClient.java"><i>View Source</i></a>
 * </p>
 * 
 * @author zl
 * @version 3.0
 * @since 1.0
 */
public class NettyClient {
    private ScheduledExecutorService exector = Executors.newScheduledThreadPool(1);
    private NioEventLoopGroup group = new NioEventLoopGroup();

    public void connect(String host, int port) throws Exception {
        try {
            Bootstrap boot = new Bootstrap();
            boot.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, 0, 0));
                            ch.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());
                            ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                            ch.pipeline().addLast("loginAuthHandler", new LoginAuthReqHandler());
                            ch.pipeline().addLast("heartBeatHandler", new HeartBeatReqHandler());
                        }
                    });
            // 发起异步连接操作
            ChannelFuture future = boot.connect(new InetSocketAddress(host, port),new InetSocketAddress(NettyConstant.LOCALIP,
                    NettyConstant.LOCAL_PORT)).sync();

            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // 所有资源释放完成之后，清空资源，再次发起重连操作

            exector.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        try {
                            connect(NettyConstant.REMOTEIP, NettyConstant.PORT); // 发起重连操作
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        try {
            new NettyClient().connect(NettyConstant.LOCALIP, NettyConstant.PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
