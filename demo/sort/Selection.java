/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)Selection.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年5月26日
 */
package org.demo.sort;

import org.apache.commons.lang.ArrayUtils;

/** 
 * 选择排序
 * 
 * <p>
 * <a href="Selection.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class Selection {
    public static void sort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            int temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
                    
        }
    }
    
    public static void main(String[] args) {
        int[] arr = new int[] {1,2,3,4,5,1,2,31,23,123,4232,3,13123,123};
        sort(arr);
        System.out.println(ArrayUtils.toString(arr));
    }
}
