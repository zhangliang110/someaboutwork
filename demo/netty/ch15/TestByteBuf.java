/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TestByteBuf.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月9日
 */
package org.demo.netty.ch15;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;

/** 
 * 
 * ByteBuf相关API使用
 * <a href="TestByteBuf.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TestByteBuf {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("anihao".getBytes());
        ByteBuf buf = Unpooled.wrappedBuffer(buffer);
        int num = buf.bytesBefore("n".getBytes()[0]);
        System.out.println(num);
        for (Byte s : "anihao".getBytes()) {
            System.out.println(s);
        }
        System.out.println("=====================");
        for (Byte s : "n".getBytes()) {
           System.out.println(s);
        }
        byte a = '\r';
        System.out.println(a == '\r');
        
        AtomicIntegerFieldUpdater<Updater> ac = AtomicIntegerFieldUpdater.newUpdater(Updater.class, "count");
        Updater up = new Updater();
        int count = ac.incrementAndGet(up);
        System.out.println(up.getCount());
        
        UnpooledByteBufAllocator alc = new UnpooledByteBufAllocator(false);
        buf = alc.buffer(100);
        buf.writeBytes("hello2".getBytes());
        System.out.println(buf.writableBytes());
        ByteBuf buf2 = alc.buffer(1002);
        buf2.writeBytes("hello".getBytes());
        
        int abc = 1;
        abc &= ~3;
        
    }
}
