/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ReadCompletionHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <p>
 * <a href="ReadCompletionHandler.java"><i>View Source</i></a>
 * </p>
 * 
 * @author zl
 * @version 3.0
 * @since 1.0
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
    private final AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip(); // 为下次读做准备
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body, "UTF-8");
            System.out.println("The TimeServer receive order:" + req);
            String currtTime = "QUERY TIME ORDER".equals(req) ? new Date(System.currentTimeMillis()).toString() : "Bad order";
            this.doWrite(currtTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doWrite(String content) {
        if (StringUtils.isNotBlank(content)) {
            byte[] bytes = content.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            this.channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    // 如果没有发送完成，继续发送
                    if (buffer.hasRemaining()) {
                       channel.write(buffer, buffer, this);
                    } else {
                        System.out.println("send success once");
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (Exception e) {
                        //ignore the ex
                        e.printStackTrace();
                    }
                }
            });
        }
        
    }
    
    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
        try {
            this.channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
