/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)AbstractHbaseTemplate.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月15日
 */
package org.demo.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.demo.hbase.utils.AdminCallBack;
import org.demo.hbase.utils.HbaseTemplate;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="AbstractHbaseTemplate.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public abstract class AbstractHbaseTemplate {
    
    public abstract HbaseTemplate getHbaseTemplate();
    
    public boolean tableExists(final String tableName) {
        return getHbaseTemplate().execute(
                new AdminCallBack<Boolean>() {
                    @Override
                    public Boolean doInAdmin(Admin admin) throws Exception {
                        return admin.tableExists(TableName.valueOf(tableName));
                    }
                }
                );
        
    }

}
