/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeClientHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/** 
 * 异步读写消息实际处理线程
 * <p>
 * <a href="TimeClientHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
/** 
 * 功能描述
 * 
 * <p>
 * <a href="TimeClientHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TimeClientHandler implements Runnable{
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel sc;
    private volatile boolean stop;
    
    
    public TimeClientHandler(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            sc = SocketChannel.open();
            sc.configureBlocking(false);
        } catch (Exception e) {
            System.out.println("error when init the client");
            System.exit(-1);
        }
    }
    
    @Override
    public void run() {
        try {
            this.doConnect();
        } catch (Exception e) {
            System.out.println("error when get the connect");
            System.exit(-2);
        }
        
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIt = keys.iterator();
                SelectionKey key = null;
                while(keyIt.hasNext()) {
                    key = keyIt.next();
                    keyIt.remove();
                    try {
                        this.handleKey(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (key != null) {
                            //当发生异常时，将key失效并释放资源
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error");
                System.exit(-3);
            }
        }
        //关闭多路复用器
        if (selector != null) {
            try {
                selector.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 函数的目的/功能
     * @param key
    */
    private void handleKey(SelectionKey key) throws IOException{
        if (key.isValid()) {
            //判断是否连接成功
            SocketChannel sc = (SocketChannel)key.channel();
            if (key.isConnectable()) {
                //如果是连接请求
                if (sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_READ);
                    this.doWrite(sc);
                } else {
                    System.exit(-4);    //连接失败
                }
            }
            if (key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("Now is " + body);
                    this.stop = true;
                } else if (readBytes < 0) {
                    //说明链路关闭
                    key.cancel();
                    sc.close();
                } else {
                    //读到字节数为0  忽略不处理
                }
                
            }
            
            
        }
    }

    /**
     * 获取连接
    */
    private void doConnect() throws IOException {
        //如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
        if (sc.connect(new InetSocketAddress(host, port))) {
            sc.register(selector, SelectionKey.OP_READ);
            this.doWrite(sc);
        } else {
            sc.register(selector, SelectionKey.OP_CONNECT);
        }
    }
    
    private void doWrite(SocketChannel sc) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();     //读之前必须调用filp方法
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            System.out.println("Send order 2 server successed");
        }
    }
}
