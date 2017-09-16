/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TableFactory.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月15日
 */
package org.demo.hbase.utils;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;

/** 
 * Table工厂接口
 * <p>
 * <a href="TableFactory.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public interface TableFactory {
    
    /**
     * 创建table
     * @param connection
     * @param tableName
     * @return
    */
    Table createTable(Connection connection, byte[] tableName);
    
    /**
     * 创建admin
     * @param connection
     * @return
     * @throws IOException
    */
    Admin createAdmin(Connection connection) throws IOException;
    
    void releaseTable(final Table table) throws IOException;
    
    void releaseAdmin(final Admin admin) throws IOException;
}
