package com.cliffyuan.gc.base;

import org.junit.Test;

import java.util.Date;

/**
 * Created by cliffyuan on 14-7-29.
 */
public class GC {

    private static final int _1KB = 1024;

    private static final int _1MB = _1KB * 1024;

    public static void main(String[] args)throws Exception{
        testParallelOldGC2();
    }


    /**
     * -server -Xms30m -Xmx30m -XX:PermSize=10m -XX:MaxPermSize=10m -Xss256k -XX:-UseParallelOldGC -XX:InitialTenuringThreshold=2 -XX:MaxTenuringThreshold=2 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+PrintTenuringDistribution
     *
     * 其他大小：1000kb         PSYoungGen      total 9216K, used 8168K
     * gc后大小：696kb         PSYoungGen      total 9216K, used 696K
     * 回收了：304kb
     */
    public static void testParallelOldGC()throws Exception{
        byte[] byte1=new byte[_1MB*2];
        byte[] byte2=new byte[_1MB*5];
        System.out.println("0");
        byte[] byte3=new byte[_1MB];//执行到这时候发生了gc,将7m从yung->old
        //  PSOldGen        total 20480K, used 0K
        //  PSOldGen        total 20480K, used 7168K

        System.out.println("1");
        Thread.sleep(15000);
        System.out.println("2"+byte1+byte2+byte3);
    }


    /**
     * -server -Xms30m -Xmx30m -XX:PermSize=10m -XX:MaxPermSize=10m -Xss256k -XX:-UseParallelOldGC -XX:InitialTenuringThreshold=2 -XX:MaxTenuringThreshold=2 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+PrintTenuringDistribution
     *
     * 其他大小：1000kb         PSYoungGen      total 9216K, used 8168K
     * gc后大小：696kb         PSYoungGen      total 9216K, used 696K
     * 回收了：304kb
     */
    public static void testParallelOldGC2()throws Exception{
        byte[] byte1=new byte[_1MB*2];
        byte[] byte2=new byte[_1MB*5];
        System.out.println("0");
        byte[] byte3=new byte[_1MB*2];//执行到这时候发生了gc,将7m从yung->old
        byte[] byte4=new byte[_1MB*5];



        System.out.println("1");

        byte[] byte5=new byte[_1MB*1];
        System.out.println("2");
        Thread.sleep(15000);
        System.out.println("3"+byte1+byte2+byte3+byte4);
    }

}
