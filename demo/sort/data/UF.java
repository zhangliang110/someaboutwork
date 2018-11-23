/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)UF.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年11月20日
 */
package org.demo.sort.data;

/** 
 * 
 * 并查集的实现：
 * 首先在地图上给你若干个城镇，这些城镇都可以看作点，然后告诉你哪些对城镇之间是有道路直接相连的。最后要解决的是整幅图的连通性问题。
 * 比如随意给你两个点，让你判断它们是否连通，或者问你整幅图一共有几个连通分支，也就是被分成了几个互相独立的块。像畅通工程这题，问还需要修几条路，
 * 实质就是求有几个连通分支。如果是1个连通分支，说明整幅图上的点都连起来了，不用再修路了；如果是2个连通分支，则只要再修1条路，从两个分支中各选一个点，把它们连起来，那么所有的点都是连起来的了；如果是3个连通分支，则只要再修两条路…
 * 
 * 
 * 4 2 1 3 4 3
         第一行告诉你，一共有4个点，2条路。下面两行告诉你，1、3之间有条路，4、3之间有条路。
        那么整幅图就被分成了1-3-4和2两部分。只要再加一条路，把2和其他任意一个点连起来，畅通工程就实现了，那么这个这组数据的输出结果就是1。
        好了，现在编程实现这个功能吧，城镇有几百个，路有不知道多少条，而且可能有回路。 这可如何是好？
 * <p>
 * <a href="UF.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class UF {
    private int[] s;
    private int count;  //记录并查集中自己和的个数
    
    
    public UF(int numElements) {
        s = new int[numElements];
        count = numElements;
        //初始化并查集，代表新建了s.length个互不相交的集合
        for (int i = 0; i < s.length; i++) {
            s[i] = -1;  //s[i]存储的是高度（秩）信息
        }
    }
    
    public void unionByHeight(int root1, int root2) {
        if(find(root1) == find(root2)) {
            return ;    //root1 与root2 已经连通
        }
        
        if (s[root2] < s[root1]) {
            s[root1] = root2;
        } else {
            if (s[root1] == s[root2]) {
                s[root1]--; //将root1的高度加1
            }
            s[root2] = root1;
        }
        count--;    //每union一次，子树数目减一
    }
    
    public int find(int x) {
        if (s[x] < 0) {
            return x;
        } else {
            return find(s[x]);
        }
    }
    
    public void union(int root1, int root2) {
        s[root1] = root2;
    }
    
    public static void main(String[] args) {
        UF uf = new UF(10);
        uf.union(1, 2);
        System.out.println(uf.find(1));
        System.out.println(uf.find(2));
        System.out.println(uf.find(3));
        
    }
    
    
}
