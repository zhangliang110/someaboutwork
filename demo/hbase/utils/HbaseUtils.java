/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)HbaseUtils.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月15日
 */
package org.demo.hbase.utils;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;

import com.google.common.base.Preconditions;

/** 
 * Hbase获取获取相应 Table ，admin ，connection 的工具类
 * <p>
 * <a href="HbaseUtils.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class HbaseUtils {

    public static Table getTable(String tableName, Connection connection) {
        return getTable(tableName, connection, getCharset(null), null);
    }
    
    public static Table getTable(String tableName, Connection connection, Charset charset, TableFactory facotry) {
        Preconditions.checkNotNull(facotry);
        Table table = null;
        try {
            table = facotry.createTable(connection, tableName.getBytes(charset));   
        } catch (Exception e) {
            //TODO 转换为HBase业务异常抛出
            e.printStackTrace();
        }
        return table;
    }

    public static Admin getAdmin(Connection connection, TableFactory factory) {
        Preconditions.checkNotNull(factory);
        Admin admin = null;
        try {
            admin = factory.createAdmin(connection);
        } catch (Exception e) {
            //TODO 转换为Hbase业务异常抛出
            e.printStackTrace();
        }
        return admin;
    }
    
    public static Charset getCharset(String charset) {
        return StringUtils.isBlank(charset) ? Charset.forName("UTF-8") : Charset.forName(charset);
    }
    
    
    public static void releaseTable(Table table, TableFactory factory) {
        try {
            factory.releaseTable(table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void releaseAdmin(Admin admin, TableFactory factory) {
        try {
            factory.releaseAdmin(admin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
