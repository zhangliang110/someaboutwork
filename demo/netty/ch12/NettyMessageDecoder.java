/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)NettyMessageDecoder.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月25日
 */
package org.demo.netty.ch12;


import java.util.HashMap;
import java.util.Map;

import org.demo.netty.ch12.struct.Header;
import org.demo.netty.ch12.struct.NettyMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/** 
 * 
 * <p>
 * <a href="NettyMessageDecoder.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder{

    private MarshallingDecoder marshallingDecoder; 
    
    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int lengthToSkip) throws Exception {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, lengthToSkip);
        marshallingDecoder = new MarshallingDecoder();
    }
    
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf)super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionId(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());
        
        int size = frame.readInt(); //
        if (size > 0) {
            Map<String,Object> attch = new HashMap<>(size);
            int keySize = 0;
            byte[] keyArray = null;
            String key = null;
            for (int i = 0; i < size; i++) {
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keyArray);
                key = new String(keyArray, "UTF-8");
                attch.put(key, marshallingDecoder.decode(frame));
            }
        }
        if (frame.readableBytes() > 4) {
            message.setBody(marshallingDecoder.decode(frame));
        }
        message.setHeader(header);
        return message;
        
    }
    
}
