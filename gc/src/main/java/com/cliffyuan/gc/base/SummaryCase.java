package com.cliffyuan.gc.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cliffyuan on 14-7-29.
 *
 * -Xms30m -Xmx30m -Xmn10m -XX:+UseParallelGC -XX:+PrintGCDetails
 *
 * 总结上面分析的策略，可以看到采用Parallel GC的情况下，当YGC触发时，会有两个检查：
 1)、在YGC执行前，min(目前新生代已使用的大小,之前平均晋升到old的大小中的较小值) > 旧生代剩余空间大小 ? 不执行YGC，直接执行Full GC : 执行YGC；
 2)、在YGC执行后，平均晋升到old的大小 > 旧生代剩余空间大小 ? 触发Full GC ： 什么都不做。

 *
 */
public class SummaryCase {
    public static void main(String[] args) throws Exception{
        List<Object> caches=new ArrayList<Object>();
        for(int i=1;i<8;i++){
            System.out.println(i);

            //当执行第7次的时候，
            //根据上面的1）计算 min(6m,3m)>(20m-12m),因此执行YGC，
            caches.add(new byte[1024*1024*3]);
            //根据上面的2)计算 3m>(20m-18m),因此执行FGC，

        }
        caches.clear();
        for(int i=0;i<2;i++){
            System.out.println(i);
            caches.add(new byte[1024*1024*3]);
        }
        Thread.sleep(10000);
    }
}
