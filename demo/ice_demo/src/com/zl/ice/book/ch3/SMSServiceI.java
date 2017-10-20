/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)SMSServiceI.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月20日
 */
package com.zl.ice.book.ch3;

import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book.OnlineBookPrx;
import com.hp.tel.ice.book.OnlineBookPrxHelper;
import com.hp.tel.ice.message._SMSServiceDisp;

import Ice.Communicator;
import Ice.Current;
import Ice.Logger;
import Ice.ObjectAdapter;
import Ice.ObjectPrx;
import IceBox.Service;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="SMSServiceI.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class SMSServiceI extends _SMSServiceDisp implements Service {
    
    private static final long serialVersionUID = -2780313940405185969L;
    private ObjectAdapter _adapter;
    private Logger log;
    
    public void sendSMS(String msg, Current __current) {
        System.out.println("send message: " + msg);
        //ice服务之间进行调用
        if (msg.startsWith("book")) {
            ObjectPrx proxy = _adapter.getCommunicator().stringToProxy("OnlineBook");
            OnlineBookPrx bookPorxy = OnlineBookPrxHelper.checkedCast(proxy);
            Message bookMsg = new Message();
            bookMsg.content = "inner service interactive";
            bookMsg.name = "zl";
            bookMsg.price = 100;
            bookMsg.type = 3;
            bookPorxy.bookTick(bookMsg);
        }
    }

    @Override
    public void start(String name, Communicator communicator, String[] arg2) {
        log = communicator.getLogger().cloneWithPrefix(name);
        //创建ObjectAdapter，这里和Service同名
        _adapter = communicator.createObjectAdapter(name);
        //创建servant并激活
        Ice.Object object = this;
        _adapter.add(object, communicator.stringToIdentity(name));
        _adapter.activate();
        log.trace("control", " started  ");
    }

    @Override
    public void stop() {
        log.trace("control", " end  ");
        System.out.println("control ---- stop");
        _adapter.destroy();
    }

}
