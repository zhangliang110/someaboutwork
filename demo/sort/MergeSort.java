/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MergeSort.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年5月29日
 */
package org.demo.sort;

import org.apache.commons.lang.ArrayUtils;

/** 
 * 归并排序
 * 
 * <p>
 * <a href="MergeSort.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class MergeSort {
    private static int[] aux;
    
    private static void merge(int[] a, int l, int m, int h) {
         int i = l, j = m + 1;
         for (int k = l; k <= h; k++) {
             aux[k] = a[k]; //将数据复制到辅助组
         }
         for (int k = l; k <= h; k++) {
              if (i > m) {
                 a[k] = aux[j++];
              }else if (j > h) {
                 a[k] = aux[i++];
             } else if (aux[i] < a[j]) {
                 a[k] = aux[i++];
             } else {
                 a[k] = aux[j++];
             }
         }
    }
    
    public static void sort(int[] a) {
        aux = new int[a.length];
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int l, int h) {
        if (h <= l)
            return;
        int mid = l + (h - l) / 2;
        sort(a, l, mid);
        sort(a, mid + 1, h);
        merge(a, l, mid, h);
    }
    
    public static void main(String[] args) {
        int[] arr = new int[] {1,2,3,4,5,1,2,31,23,123,4232,3,13123,123,12,44,1,444444,111122,33333,4444,2333,4444,555};
        sort2(arr);
        System.out.println(ArrayUtils.toString(arr));
    }
    
    
    
    /**
     * 自底而上的归并排序···？ 似乎有些问题
     * @param a
    */
    private static void sort2(int[] a) {
        int length = a.length;
        aux = a;
        for (int i = 1; i < length; i += i) {
            for (int j = 0; j < length - i; j += i + i) {
                merge(a, j, j + i - 1, Math.min(j + i + i - 1, length - 1));
                
            }
            
        }
    }
    
}
