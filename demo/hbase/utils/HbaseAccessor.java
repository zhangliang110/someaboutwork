/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)HbaseAccessor.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月15日
 */
package org.demo.hbase.utils;

import java.nio.charset.Charset;

import org.apache.hadoop.hbase.client.Connection;

/** 
 * 带有 hbaseFactory 等连接hbase的属性字段
 * <p>
 * <a href="HbaseAccessor.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class HbaseAccessor {
    private TableFactory tableFactory;
    private Connection connection;
    private String encoding;
    private Charset charset = HbaseUtils.getCharset(encoding);
    
    /**
     * @return the charset
     */
    public Charset getCharset() {
        return charset;
    }
    /**
     * @return the tableFactory
     */
    public TableFactory getTableFactory() {
        return tableFactory;
    }

    /**
     * @param tableFactory the tableFactory to set
     */
    public void setTableFactory(TableFactory tableFactory) {
        this.tableFactory = tableFactory;
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }
    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }
    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    
}
