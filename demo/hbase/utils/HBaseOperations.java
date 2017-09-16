/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)HBaseOperations.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月15日
 */
package org.demo.hbase.utils;


/** 
 * 功能描述
 * 
 * <p>
 * <a href="HBaseOperations.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public interface HBaseOperations {

    <T> T execute(String tableName, TableCallBack<T> callBack);
    
    <T> T execute(AdminCallBack<T> action);
}
