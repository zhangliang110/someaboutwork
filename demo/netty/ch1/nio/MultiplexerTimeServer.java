/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MultiplexerTimeServer.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/** 
 * 多路复用类，是一个独立的线程，负责轮询多路复用器selector，可处理多个客户端的并发
 * 
 * <p>
 * <a href="MultiplexerTimeServer.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class MultiplexerTimeServer implements Runnable{
    private Selector selector;
    private ServerSocketChannel servChannel;
    private volatile boolean stop;
    
    /**
     * 初始化多路复用器,绑定监听端口 
     * 创建多路复用器Selector，ServerSocketChannel。并将ServerSocketChannel设置为非阻塞模式
     * 然后将 ServerSocketChannel 注册到Selector中，并监听SelectionKey.OP_ACCEPT操作位
     */
    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            servChannel = ServerSocketChannel.open();
            servChannel.configureBlocking(false);   //必须配置为非阻塞的channel
            servChannel.socket().bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), port), 1024);
            servChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("TimeServer start in prot:" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    public void stop() {
        this.stop = true;
    }
    
    /* 
     * 注册要是遍历SelectionKey
     */
    @Override
    public void run() {
        while(!stop) {
            try {
                selector.select(1000);  //休眠时间为1s，无论是否有读写事件发生，selector每一秒都会唤醒一次
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIt = keys.iterator();
                SelectionKey key = null;
                while (keyIt.hasNext()) {
                    key = keyIt.next();
                    keyIt.remove();
                    try {
                        this.handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
        //多路复用器关闭后，所有注册在上面的channel，和Pipe等资源都会自动去注册并关闭
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 根据SelectionKey的操作位来判断网络事件的类型，通过ServerSocketChannel的accept接受客户端连接请求并创建SocketChannel实例
     * 
     * 完成上述操作就相当于完成了 TCP的三次握手操作
     * @param key
     * @throws IOException
    */
    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //处理新接入的请求消息
            if (key.isAcceptable()) {
                //accept the new connection
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                //Add new connection to the selector
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                //read the data
                SocketChannel sc = (SocketChannel)key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();  //为下次读做准备
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);  //操作缓冲区可读字节到bytes数组中
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order:" + body);
                    String currentTime = "QUERY TIME ORDER".equals(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    this.doWrite(sc, currentTime);
                } else if (readBytes < 0) {
                    //对链路进行关闭
                    key.cancel();
                    sc.close();
                } else 
                    ;   //读到0字节忽略
            } 
        }
    }
    
    /**
     * 将应答消息异步发送给客户端
     * @param sc
     * @param sendMessage
     * @throws IOException
    */
    private void doWrite(SocketChannel sc, String sendMessage) throws IOException {
        if (StringUtils.isNotBlank(sendMessage)) {
            byte[] bytes = sendMessage.getBytes("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(bytes);
            buffer.flip();
            sc.write(buffer);
        }
    }
}
