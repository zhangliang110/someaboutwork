/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MyRegistryClient.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月20日
 */
package com.zl.ice.book.ch3;

import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book.OnlineBookPrx;
import com.hp.tel.ice.book.OnlineBookPrxHelper;
import com.hp.tel.ice.message.SMSServicePrx;
import com.hp.tel.ice.message.SMSServicePrxHelper;

import Ice.ObjectPrx;

/**
 * 功能描述
 * 
 * <p>
 * <a href="MyRegistryClient.java"><i>View Source</i></a>
 * </p>
 * 
 * @author zl
 * @version 3.0
 * @since 1.0
 */
public class MyRegistryClient {

    public static void main(String[] args) {
        int status = 0;
        Ice.Communicator ice = null;
        try {
            String[] initParams = {"--Ice.Default.Locator=IceGrid/Locator:tcp -h localhost -p 4061"};
            ice = Ice.Util.initialize(initParams);
            // 传入远程服务单元的名称，网络协议，IP及端口，获取OnlineBook的代理，这里还是使用stringToProxy
            ObjectPrx proxy = ice.stringToProxy("OnlineBook@OnlineBookAdapter");
            OnlineBookPrx onlineBookProxy = OnlineBookPrxHelper.checkedCast(proxy);
            if (onlineBookProxy == null) {
                throw new Error("invalid proxy");
            }
            // 调用服务方法
            Message msg = new Message();
            msg.content = "hello world";
            msg.name = "zl";
            msg.price = 100;
            msg.type = 2;
            System.out.println(onlineBookProxy.bookTick(msg));

            proxy = ice.stringToProxy("SMSService@SMServiceAdapter");
            SMSServicePrx smsServiceProxy = SMSServicePrxHelper.checkedCast(proxy);
            if (smsServiceProxy == null) {
                throw new Error("invalid smsServiceProxy");
            }
            smsServiceProxy.sendSMS("book hello");
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        } finally {
            if (ice != null) {
                ice.destroy();
            }
        }
        System.exit(status);
    }

}
