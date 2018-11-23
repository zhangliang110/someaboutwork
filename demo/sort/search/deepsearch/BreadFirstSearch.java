/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)BreadFirstSearch.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年11月5日
 */
package org.demo.sort.search.deepsearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/** 
 * 广度优先搜索，查找二维数组中的最优解
 * 
 * <p>
 * <a href="BreadFirstSearch.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class BreadFirstSearch {
    private int row;    //行
    private int col;    //列
    
    //定义方向常量
    private final int DOWN = 1;
    private final int RIGHT = 2;
    
    //代表每一个搜索节点
    private class SearchItem {
        private int curX;
        private int curY;
        private int val;
        private int dir;
        private SearchItem lastItem;
        
        public SearchItem(int curX, int curY, int val, int dir) {
            super();
            this.curX = curX;
            this.curY = curY;
            this.val = val;
            this.dir = dir;
        }
    }
    
    //最终得到的结果
    private SearchItem bestItem = null;
    
    //广搜的存储队列
    private List<SearchItem> statusToSearch = new LinkedList<>();
    
    private boolean replaceSmallerSearchItem(SearchItem newItem) {
        boolean find = false;
        for (int i = 0; i < statusToSearch.size(); i++) {
            SearchItem searchItem = statusToSearch.get(i);
            if (searchItem.curX == newItem.curX && searchItem.curY == newItem.curY) {
                find = true;
                //相当于对比best(i,j)
                if(searchItem.val < newItem.val) {
                    statusToSearch.remove(i);
                    statusToSearch.add(i, newItem);
                }
                break;
            }
        }
        return find;
    }
    
    //广搜
    private void search(int[][] matrix) {
        //搜索队列不为空
        while(!statusToSearch.isEmpty()) {
            //从搜索队列取出第一个
            SearchItem searchItem = statusToSearch.remove(0);
            int curX = searchItem.curX;
            int curY = searchItem.curY;
            int val = searchItem.val;
            
            //如果已经搜索到
            if (curX == row - 1 && curY == col - 1) {
                bestItem = searchItem;
            }
            
            //可往下搜
            if (curX < row - 1) {
                SearchItem newItem = new SearchItem(curX + 1, curY, val + matrix[curX + 1][curY], DOWN);
                newItem.lastItem = searchItem;
                if (!replaceSmallerSearchItem(newItem)) {
                    statusToSearch.add(newItem);
                }
            }
            
            //可往右搜
            if (curY < col - 1) {
                SearchItem newItem = new SearchItem(curX, curY + 1, val + matrix[curX][curY + 1], RIGHT);
                newItem.lastItem = searchItem;
                if(!replaceSmallerSearchItem(newItem)){
                    statusToSearch.add(newItem);
                }
            }
        }
    }
    
    
    private int getMaxReward(int[][] matrix) {
        row = matrix.length;
        col = matrix[0].length;
        int val = matrix[0][0];
        SearchItem searchItem = new SearchItem(0, 0, val, 0);
        statusToSearch.add(searchItem);
        search(matrix);
        return bestItem.val;
    }
    private void printBestPath() {
        List<Integer> bestPath = new ArrayList<>();
        SearchItem searchItem = bestItem;
        while(searchItem != null) {
            bestPath.add(searchItem.dir);
            searchItem = searchItem.lastItem;
        }
        Collections.reverse(bestPath);
        bestPath.forEach(dir -> System.out.println(dir == 0 ? "开始节点": dir == DOWN ? "下" : "右"));
    }
    
    public static void main(String[] args) {

        int[][] matrix1 = {
            {300, 500, 560, 400, 160},
            {1000, 100, 200, 340, 690},
            {600, 500, 500, 460, 320},
            {300, 400, 250, 210, 760}
        };

        int[][] matrix2 = {
            {300, 500, 2560, 400},
            {1000, 100, 200, 340},
            {600, 500, 500, 460},
            {300, 400, 250, 210},
            {860, 690, 320, 760}
        };

        BreadFirstSearch dp = new BreadFirstSearch();
        System.out.println(dp.getMaxReward(matrix1));
        dp.printBestPath();
        System.out.println(dp.getMaxReward(matrix2));
    }
    
}
