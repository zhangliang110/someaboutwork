/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MsgpackEncoder.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月21日
 */
package org.demo.netty.ch7;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/** 
 * msgpack编码器开发
 * <p>
 * <a href="MsgpackEncoder.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
        try {
            MessagePack msgpack = new MessagePack();
            //Serialize
            byte[] raw = msgpack.write(msg);
            out.writeBytes(raw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
