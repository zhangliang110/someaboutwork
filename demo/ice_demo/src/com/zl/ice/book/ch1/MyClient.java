/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MyClient.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月19日
 */
package com.zl.ice.book.ch1;

import Ice.ObjectPrx;

/** 
 * 
 * ice服务客户端
 * <p>
 * <a href="MyClient.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class MyClient {
    public static void main(String[] args) {
        int status = 0;
        Ice.Communicator ice = null;
        try {
            ice = Ice.Util.initialize(args);
            ObjectPrx base = ice.stringToProxy("MyService:default -p 10000");
            
            //向下转型获取 具体的代理接口
            MyservicePrx myServicePrx = MyservicePrxHelper.checkedCast(base);
            if (myServicePrx == null) {
                throw new Error("can't find the myserivce prx");
            }
            //调用服务方法
            String rt = myServicePrx.hellow();
            System.out.println(rt);
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
