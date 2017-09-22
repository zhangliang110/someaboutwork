/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)AsyncTimeClientHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * 异步编程客户端处理类
 * <p>
 * <a href="AsyncTimeClientHandler.java"><i>View Source</i></a>
 * </p>
 * 
 * @author zl
 * @version 3.0
 * @since 1.0
 */
public class AsyncTimeClientHandler implements Runnable, CompletionHandler<Void, AsyncTimeClientHandler> {
    private AsynchronousSocketChannel clientChannel;
    private String host;
    private int port;
    private CountDownLatch latch;

    public AsyncTimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            clientChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        clientChannel.connect(new InetSocketAddress(host, port), this, this);
        try {
            latch.await();  //防止前一步异步操作没有执行完就线程就推出。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        clientChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                if (buffer.hasRemaining()) {
                    //如果没有写完则继续写
                    clientChannel.write(buffer, buffer, this);
                } else {
                    //如果有就直接读信息
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    clientChannel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {

                        @Override
                        public void completed(Integer result, ByteBuffer buffer) {
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            String body;
                            try {
                                body = new String(bytes, "UTF-8");
                                System.out.println("Now is" + body);
                                latch.countDown();
                            } catch (Exception e) {
                                // DO NOTING
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try {
                                clientChannel.close();
                                latch.countDown();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    clientChannel.close();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

        });

    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
        exc.printStackTrace();
        try {
            clientChannel.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}
