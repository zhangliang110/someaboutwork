/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)AsyncTimeServerHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.StringUtils;

/** 
 * 异步编程实现的时间服务器处理线程类
 * 
 * <p>
 * <a href="AsyncTimeServerHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class AsyncTimeServerHandler implements Runnable{
    private int port;
    private String host;
    CountDownLatch latch;
    AsynchronousServerSocketChannel asyncSc;

    /**
     * 
     */
    public AsyncTimeServerHandler() {
    }
    
    public AsyncTimeServerHandler(String host, int port) {
        this.port = port;
        this.host = host == null ? "localhost" : host;
        try {
            asyncSc = AsynchronousServerSocketChannel.open();
            asyncSc.bind(new InetSocketAddress(host, port), 1024);
            System.out.println("The asynchronousServer start in port:" + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        latch = new CountDownLatch(1);
        this.doAccpet();
        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void doAccpet() {
        asyncSc.accept(this, new AccpetCompletionHandler());
    }

}
