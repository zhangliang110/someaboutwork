/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MyServerStarter.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月19日
 */
package com.zl.ice.book.ch1;

import Ice.ObjectAdapter;

/** 
 * 服务启动类
 * 
 * <p>
 * <a href="MyServerStarter.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class MyServerStarter {
    public static void main(String[] args) {
        int status = 0;
        Ice.Communicator ic = null;
        try {
            ic = Ice.Util.initialize(args);    //初始化这个communicator对象
            ObjectAdapter serviceAdapter = ic.createObjectAdapterWithEndpoints("MyServiceAdapter", "default -p 10000");
            MyServiceImpl servant = new MyServiceImpl();    //实例化一个servant （最终的服务对象）
            serviceAdapter.add(servant, Ice.Util.stringToIdentity("MyService"));
            serviceAdapter.activate();
            //让服务在退出之前，一直持续对请求进行监听
            System.out.println("server started");
            ic.waitForShutdown();
        } catch (Exception e) {
          e.printStackTrace();  
          status = 1;
        } finally {
            if (ic != null ) {
                ic.destroy();
            }
        }
        System.exit(status);
    }
}
