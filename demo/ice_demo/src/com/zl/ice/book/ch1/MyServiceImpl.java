/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MyServiceImpl.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月19日
 */
package com.zl.ice.book.ch1;

import Ice.Current;

/** 
 * 
 * <p>
 * <a href="MyServiceImpl.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class MyServiceImpl extends _MyserviceDisp{

    private static final long serialVersionUID = 257326727650842827L;

    @Override
    public String hellow(Current __current) {
        System.out.println(__current.ctx);
        return "hello world";
    }

}
