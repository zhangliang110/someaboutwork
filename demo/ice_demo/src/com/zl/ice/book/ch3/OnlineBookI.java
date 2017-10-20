/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)OnlineBookI.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月19日
 */
package com.zl.ice.book.ch3;

import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book._OnlineBookDisp;

import Ice.Communicator;
import Ice.Current;
import Ice.ObjectAdapter;
import IceBox.Service;

/** 
 * 
 * 使用ice box 启动ice服务
 * <p>
 * <a href="OnlineBookI.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class OnlineBookI extends _OnlineBookDisp implements Service{
    
    private ObjectAdapter _adapter;
    
    @Override
    public Message bookTick(Message msg, Current __current) {
        System.out.println(OnlineBookI.class.getSimpleName() + "bookTick call");
        return msg;
    }

    @Override
    public void start(String name, Communicator communicator, String[] args) {
        _adapter = communicator.createObjectAdapter(name);
        _adapter.add(this, communicator.stringToIdentity(name));
        _adapter.activate();
        System.out.println(name + "started");
    }

    @Override
    public void stop() {
        System.out.println(this.getClass().getName() + "stop");
        _adapter.destroy();
    }

}
