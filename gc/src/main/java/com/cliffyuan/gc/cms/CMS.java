package com.cliffyuan.gc.cms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuanyuanming on 14-7-28.
 *
 *
 */
public class CMS {

    /**
     * -server -Xms3g -Xmx3g -XX:PermSize=128m -XX:MaxPermSize=128m -Xss128k -XX:+UseConcMarkSweepGC -XX:CMSFullGCsBeforeCompaction=5 -XX:CMSInitiatingOccupancyFraction=50 -XX:CMSInitiatingPermOccupancyFraction=50 -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintGCDetails -XX:+PrintGCDateStamps
     *
     * 按照默认值进行计算
     * （1）-XX:NewRatio      默认为2，则3g*1/3=1g
     * （2）-XX:SurvivorTatio 默认为8，则1g*0.8=0.8g
     *
     * 即：
     * (1)年轻代大小总1g:
     * eden=0.8g,survivorfrom=0.1g,survivorto=0.1g
     *（2）年老代总大小2g
     *
     *
     */
    @Test
    public void cmsTest()throws Exception{

        List<M100> all=new ArrayList<M100>();
        int i=0;
        for(;;){
            all.add(new M100());
            i++;
            System.out.println(i);
            Thread.sleep(1000);
        }
    }

    /**
     * -server -Xms30m -Xmx30m -XX:PermSize=10m -XX:MaxPermSize=10m -Xss128k -XX:-UseParallelOldGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps
     *
     * 按照默认值进行计算
     * （1）-XX:NewRatio      默认为2，则30m*1/3=10m
     * （2）-XX:SurvivorTatio 默认为8，则10m*0.8=8m
     *
     * 即：
     * (1)年轻代大小总10m:
     * eden=8m,survivorfrom=1m,survivorto=1m
     *（2）年老代总大小20m
     * @throws Exception
     */
    @Test
    public void cmsPar()throws Exception{

        List<M1> all=new ArrayList<M1>();
        int i=0;
        for(;;){
            all.add(new M1());
            i++;
            System.out.println(i);
            Thread.sleep(10000);
        }
    }
}

class M100{
    private byte[] all;

    M100() {
        this.all = new byte[1024*1024*100];
    }
}

class M1{
    private byte[] all;

    M1() {
        this.all = new byte[1024*1024];
    }
}