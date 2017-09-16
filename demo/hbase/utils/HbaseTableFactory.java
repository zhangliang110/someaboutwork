/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)HbaseTableFactory.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月16日
 */
package org.demo.hbase.utils;

import java.io.IOException;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;

/** 
 * HbaseFactory 工厂类
 * <p>
 * <a href="HbaseTableFactory.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class HbaseTableFactory implements TableFactory {

    @Override
    public Table createTable(Connection connection, byte[] tableName) {
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
        } catch (IllegalArgumentException | IOException e) {
            //TODO 转换为 hbase业务异常抛出 为好
            e.printStackTrace();
        }
        return table;
    }

    /* (non-Javadoc)
     * @see org.demo.hbase.utils.TableFactory#createAdmin(org.apache.hadoop.hbase.client.Connection)
     */
    @Override
    public Admin createAdmin(Connection connection) throws IOException {
        return connection.getAdmin();
    }

    /* (non-Javadoc)
     * @see org.demo.hbase.utils.TableFactory#releaseTable(org.apache.hadoop.hbase.client.Table)
     */
    @Override
    public void releaseTable(Table table) throws IOException {
        if (table != null) {
            table.close();
        }
        
    }

    /* (non-Javadoc)
     * @see org.demo.hbase.utils.TableFactory#releaseAdmin(org.apache.hadoop.hbase.client.Admin)
     */
    @Override
    public void releaseAdmin(Admin admin) throws IOException {
        if (admin != null) {
            admin.close();
        }
        
    }
    

}
