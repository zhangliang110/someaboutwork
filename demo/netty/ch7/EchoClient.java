/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)EchoClient.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月21日
 */
package org.demo.netty.ch7;


import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/** 
 * 用netty原生的Echo程序为例，改造的客户端
 * 使用msgpack进行编解码
 * 
 * <p>
 * <a href="EchoClient.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class EchoClient {
    private final String host;
    private final int port;
    private final int sendNumber;
    
    public EchoClient(String host, int port, int sendNumber) {
        this.port = port;
        this.host = host;
        this.sendNumber = sendNumber;
    }
    
    public void run() throws Exception {
        // configure the client
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap boot = new Bootstrap();
            boot.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //解决TCP传输 粘包/粘包问题
                        ch.pipeline().addLast("frame Decoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                        ch.pipeline().addLast("frane encoder", new LengthFieldPrepender(2));
                        
                        ch.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
                        ch.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
                        ch.pipeline().addLast(new EchoClientHandler(sendNumber));
                    }
                });
            //连接服务端并等待成功响应
            ChannelFuture f = boot.connect(new InetSocketAddress(host, port)).sync();
            
            //等待客户端关闭
            f.channel().closeFuture().sync();
        } finally {
            //释放资源
            group.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1", 8088, 10).run();
    }
}
