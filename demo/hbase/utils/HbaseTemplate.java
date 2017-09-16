/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)HbaseTemplate.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月15日
 */
package org.demo.hbase.utils;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Table;

import com.google.common.base.Preconditions;

/** 
 * 具体实现Hbase操作的模版引擎 
 * <p>
 * <a href="HbaseTemplate.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class HbaseTemplate extends HbaseAccessor implements HBaseOperations{
    private boolean autoFlush = true;
    public HbaseTemplate() {}
    
    
    @Override
    public <T> T execute(String tableName, TableCallBack<T> action) {
        Preconditions.checkNotNull(tableName);
        Preconditions.checkNotNull(action);
        Table table = this.getTable(tableName);
        try {
            boolean setting = this.applyFlushSetting(table);
            T res = action.doInTable(table);
            this.flushIfNecessary(table, setting);
            return res;
        } catch (Throwable e) {
            e.printStackTrace();
            throw (RuntimeException) e;
        } finally {
            HbaseUtils.releaseTable(table, this.getTableFactory());
        }
    }
    
    private void flushIfNecessary(Table table, boolean oldAutoFlush) throws IOException {
        if (table instanceof HTable) {
            HTable htable = (HTable)table;
            htable.flushCommits();
            this.restoreFlushSettings(htable, oldAutoFlush);
        }
        
    }
    
    private boolean applyFlushSetting(Table table) {
        if(table instanceof HTable) {
            HTable htable = (HTable)table;
            boolean autoFlush = htable.isAutoFlush();
            htable.setAutoFlushTo(this.autoFlush);
            return autoFlush;
        }
        return true;
    }
    
    private void restoreFlushSettings(Table table, boolean oldFlush) {
        if (table instanceof HTable) {
            HTable htable  = (HTable)table;
            if (htable.isAutoFlush() != oldFlush) {
                htable.setAutoFlushTo(oldFlush);
            }
        }
    }


    private Table getTable(String tableName) {
        return HbaseUtils.getTable(tableName, this.getConnection(), getCharset(), getTableFactory());
    }
    
    private Admin getAdmin() {
        return HbaseUtils.getAdmin(getConnection(), this.getTableFactory());
    }


    /* (non-Javadoc)
     * @see org.demo.hbase.utils.HBaseOperations#execute(org.demo.hbase.utils.AdminCallBack)
     */
    @Override
    public <T> T execute(AdminCallBack<T> action) {
        Preconditions.checkNotNull(action);
        Admin admin = getAdmin();
        try {
            T res = action.doInAdmin(admin);
            return res;
        } catch (Exception e) {
            throw (RuntimeException) e;
        } finally {
            HbaseUtils.releaseAdmin(admin, getTableFactory());
        }
    }
    
    
    
}
