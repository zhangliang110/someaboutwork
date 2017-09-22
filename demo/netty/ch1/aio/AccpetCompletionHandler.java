/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)AccpetCompletionHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/** 
 * 通过AccpetCompletionHandler实例作为handler来接受通知消息
 * 
 * <p>
 * <a href="AccpetCompletionHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class AccpetCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler>{
    
    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        attachment.asyncSc.accept(attachment, this);    //当一个连接已经连接上后，提供给客户端异步回调让新的连接会连接上
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        result.read(byteBuffer, byteBuffer, new ReadCompletionHandler(result));
    }
    
    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }


}
