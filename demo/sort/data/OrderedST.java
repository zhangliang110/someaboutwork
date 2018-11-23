/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)OrderedST.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年11月21日
 */
package org.demo.sort.data;

import java.util.List;

/** 
 * 符号表（Symbol Table）:是一种存储键值对的数据结构，可以支持快速查找操作
 * 
 * <p>
 * <a href="OrderedST.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public interface OrderedST<Key extends Comparable<Key>, Value> {
    int size();
    
    void put(Key key, Value val);
    
    Value get(Key key);
    
    Key min();
    
    Key max();
    
    int rank(Key key);
    
    List<Key> keys(Key l, Key h);
}
