/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MsgpackDecoder.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月21日
 */
package org.demo.netty.ch7;

import java.io.IOException;
import java.util.List;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/** 
 * messageDecoder的解码器开发
 * <p>
 * <a href="MsgpackDecoder.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
        try {
            final int length = buf.readableBytes();
            final byte[] array = new byte[length];
            buf.getBytes(buf.readerIndex(), array, 0, length);
            MessagePack pack = new MessagePack();
            out.add(pack.read(array));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
