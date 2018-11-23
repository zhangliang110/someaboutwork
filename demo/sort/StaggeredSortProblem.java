/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)StaggeredSortProblem.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年11月20日
 */
package org.demo.sort;

/** 
 * 错排公式的实现:
 * 重点是：S(n) = N!*D(n)
 * 而 S(1) = 0, S(2) = 1
 * 且 S（n） = (n- 1) (S（ n - 2） + S（n - 1）)
 * 所有通过累加和累成 得到 通项公式 ：S(n) = n! [(-1)^2/2! + … + (-1)^(n-1)/(n-1)! + (-1)^n/n!].
 * 
 * <p>
 * <a href="StaggeredSortProblem.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class StaggeredSortProblem {
    
    public static void main(String[] args) {
        StaggeredSortProblem p = new StaggeredSortProblem();
        System.out.println(p.ssp(3));
        
    }
    
    public int ssp(int n) {
        if (n == 1) {
            return 0;
        }
        if (n == 2) {
            return 1;
        }
        return (n - 1) * ( ssp(n-2) + ssp(n - 1));
    }

}
