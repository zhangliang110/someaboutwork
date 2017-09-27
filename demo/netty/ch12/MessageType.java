/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MessageType.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月25日
 */
package org.demo.netty.ch12;

/** 
 * 
 * <p>
 * <a href="MessageType.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public enum MessageType {
    SERVICE_REQ((byte) 0), SERVICE_RESP((byte) 1), ONE_WAY((byte) 2), LOGIN_REQ(
            (byte) 3), LOGIN_RESP((byte) 4), HEARTBEAT_REQ((byte) 5), HEARTBEAT_RESP(
            (byte) 6);
    private byte value;
    
    private MessageType(byte value) {
        this.value = value;
    }
    
    public byte value() {
        return this.value;
    }

    
}
