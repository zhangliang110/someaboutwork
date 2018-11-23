/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)BinarySearchOrderedST.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年11月21日
 */
package org.demo.sort.data;

import java.util.List;

import com.google.common.collect.Lists;

/** 
 * 二分查找实现有序符号表：使用一对平行数组，一个存储keys，一个存储values
 * 
 * <p>
 * <a href="BinarySearchOrderedST.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class BinarySearchOrderedST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value>{
    private Key[] keys;
    private Value[] values;
    
    private int N = 0;
    
    public BinarySearchOrderedST(int cap) {
        keys = (Key[]) new Comparable[cap];
        values = (Value[]) new Object[cap];
        
    }
    @Override
    public int size() {
        return N;
    }

    @Override
    public void put(Key key, Value val) {
        int index = rank(key);
        if (index < N && keys[index].compareTo(key) == 0) {
            values[index] = val;
            return ;
        }
        for (int i = N; i > index ; i--) {
            keys[i] = keys[i - 1];
            values[i] = values[i - 1];
        }
        keys[index] = key;
        values[index] = val;
    }

    @Override
    public Value get(Key key) {
        int index = rank(key);
        if (index >= 0 && keys[index].compareTo(key) == 0) {
            return values[index];
        }
        return null;
    }

    @Override
    public Key min() {
        return keys[0];
    }

    @Override
    public Key max() {
        return keys[N - 1];
    }

    @Override
    public int rank(Key key) {
        //重点方法，使用二分法找到key对应的index
        int l = 0, h = N - 1;
        while(l <= h) {
            int m = l + (h - l) / 2;
            int cmp = key.compareTo(keys[m]);
            if (cmp == 0) {
                return m;
            } else if (cmp < 0) {
                h = m - 1;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    @Override
    public List<Key> keys(Key l, Key h) {
        int lIndex = rank(l);
        List<Key> content = Lists.newArrayList();
        while(keys[lIndex].compareTo(h) < 0) {
            content.add(keys[lIndex]);
            lIndex++;
        }
        return content;
    }
    

}
