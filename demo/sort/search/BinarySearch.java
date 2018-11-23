/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)BinarySearch.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年6月27日
 */
package org.demo.sort.search;

/** 
 * 二分查找算法
 * 
 * <p>
 * <a href="BinarySearch.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class BinarySearch {
    
    public static int binarySearch(int[] data, int target) {
        int length = data.length;
        int low = 0;
        int high = length - 1;
        while(low <= high) {
            int medium = low + (high - low) / 2;
            if (data[medium] < target) {
                low = medium + 1;
            } else if(data[medium] == target) {
                return medium;
            } else {
                high = medium - 1;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        int[] data = new int[]{1,1,2,2,3,3,4,5,23,31,123,123,4232,13123};
        int binarySearch = binarySearch(data, 123);
        System.out.println(binarySearch);
    }
}
