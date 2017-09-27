/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ByteBufferTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月25日
 */
package org.demo.netty.ch12;

import java.nio.ByteBuffer;

/** 
 * 测试下 byteBuffer各方法的作用
 * 
 * mark 与reset 方法就是一对辅助方法
 * 不管读写ByteBuffer中的position都会增加
 * 
 * remaining() 方法是说明还有多少字节可供读写
 * <p>
 * <a href="ByteBufferTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class ByteBufferTest {
    public static void main(String[] args) {
       ByteBuffer buffer = ByteBuffer.allocate(1024);
       System.out.println(buffer.limit());
       System.out.println(buffer.position());
       System.out.println(buffer.capacity());
       buffer.mark();
       buffer.putInt(1);
       System.out.println(buffer.limit());
       System.out.println(buffer.position());
       System.out.println(buffer.capacity());
       buffer.reset();
       System.out.println(buffer.getInt());
       System.out.println(buffer.position());
       buffer.clear();
       System.out.println(buffer.getInt());
       ByteBuffer slice = buffer.slice();
       System.out.println(slice.position());
       System.out.println(slice.limit());
       System.out.println(slice.remaining());
       System.out.println(slice.hasRemaining());
    }
}
