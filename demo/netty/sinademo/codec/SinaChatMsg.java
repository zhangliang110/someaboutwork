/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)SinaChatMsg.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月31日
 */
package org.demo.netty.sinademo.codec;

import org.msgpack.annotation.Message;

/** 
 * 
 * <p>
 * <a href="SinaChatMsg.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
@Message
public class SinaChatMsg {
    private String frname;
    private int msgType;    //消息类型
    private int ct; //内容类型
    private String content;

    
    public String getFrname() {
        return frname;
    }

    public void setFrname(String frname) {
        this.frname = frname;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the msgType
     */
    public int getMsgType() {
        return msgType;
    }

    /**
     * @param msgType the msgType to set
     */
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    /**
     * @return the ct
     */
    public int getCt() {
        return ct;
    }

    /**
     * @param ct the ct to set
     */
    public void setCt(int ct) {
        this.ct = ct;
    }
    
}
