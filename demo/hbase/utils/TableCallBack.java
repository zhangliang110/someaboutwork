/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TableCallBack.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月15日
 */
package org.demo.hbase.utils;

import org.apache.hadoop.hbase.client.Table;


/** 
 * 功能描述
 * 
 * <p>
 * <a href="TableCallBack.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public interface TableCallBack<T> {
    
    /**
     * Gets called by {@link HbaseTemplate} execute with an active Hbase table. Does need to care about activating or closing down the table.
     * @param table
     * @return
     * @throws Throwable
    */
    T doInTable(Table table) throws Throwable;
}
