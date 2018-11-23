/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)Insertion.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年5月28日
 */
package org.demo.sort;

import org.apache.commons.lang.ArrayUtils;

/** 
 * 插入排序
 * 
 * <p>
 * <a href="Insertion.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class Insertion {
    public static void sort(int[] arr) {
        int length = arr.length;
        for (int i = 1; i < length; i++) {
            for (int j = i; j > 0 ; j--) {
                if (arr[j - 1] > arr[j]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        int[] arr = new int[] {1,2,3,4,5,1,2,31,23,123,4232,3,13123,123};
        sort(arr);
        System.out.println(ArrayUtils.toString(arr));
    }
    
}
