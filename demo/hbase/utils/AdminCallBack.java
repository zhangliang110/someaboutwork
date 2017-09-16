/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)AdminCallBack.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月15日
 */
package org.demo.hbase.utils;

import org.apache.hadoop.hbase.client.Admin;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="AdminCallBack.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public interface AdminCallBack<T> {

    T doInAdmin(Admin admin) throws Exception;
}
