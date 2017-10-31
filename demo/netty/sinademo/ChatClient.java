/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ChatClient.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月31日
 */
package org.demo.netty.sinademo;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.demo.netty.sinademo.codec.MsgpackDecoder;
import org.demo.netty.sinademo.codec.MsgpackEncoder;
import org.demo.netty.sinademo.codec.SinaChatMsg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/** 
 *  客户端
 * <p>
 * <a href="ChatClient.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class ChatClient implements Runnable{
    private ClientHandler handler = new ClientHandler();
    private static ChatClient client = new ChatClient();
    
    public void doConnect(String host, int port) throws Exception {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap boot = new Bootstrap();
            boot.group(worker).option(ChannelOption.TCP_NODELAY, true).channel(NioSocketChannel.class)
                               .handler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {

                                @Override
                                protected void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
//                                    ch.pipeline().addLast("framedecoder", new LengthFieldBasedFrameDecoder(65536, 0, 2, 0,2));
                                    ch.pipeline().addLast("chatmsg decoder", new MsgpackDecoder());
                                    ch.pipeline().addLast("chatmsg encoder", new MsgpackEncoder());
                                    ch.pipeline().addLast(handler);
                                }
                            });
            ChannelFuture f = boot.connect(new InetSocketAddress(host, port)).sync();
            f.channel().closeFuture().sync();
        }finally {
            worker.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        client.start();
    }

    public void start() {
        new Thread(this).start();
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handler.register();
        while (true) {
            Scanner scann = new Scanner(System.in);
            String content = scann.next();
            try {
                handler.sendMessage(content, "zl");
            } catch (Exception e) {
                e.printStackTrace();
                break;
            } finally {
                scann.close();
            }
        }
    }
    
    @Override
    public void run() {
        try {
            client.doConnect("localhost", 8088);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
