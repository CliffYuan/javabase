package com.cliffyuan.gc.log;

import com.cliffyuan.gc.Object1K;
import com.cliffyuan.gc.Object1M;

/**
 * -server -Xms30m -Xmx30m -XX:+PrintGCDetails -XX:+PrintGCDateStamps
 *
 * 内存默认比例：
 * 1)Young:Old=1:2                 NewRatio设置，默认值2
 * 2)Survivor:Survivor：Eden=1:1:8 SurvivorRatio设置,默认值8
 *
 * Created by xnd on 18-3-21.
 */
public class GCLog {
    public static void main(String[] args)throws Exception {
        Thread.sleep(20000);
        System.out.println("-----start");

        allocate1m();

    }

    public static void allocate1m()throws Exception{
        for (int i=0;i<20;i++){
            Thread.sleep(2000);
            System.out.print("-");
            Object1M obj= new Object1M();
            System.out.println(i+";");
        }

//        0;1;2;3;4;5;
//        2018-03-22T00:02:32.900+0800: [GC (Allocation Failure) [PSYoungGen: 7307K->560K(9216K)] 7307K->560K(29696K), 0.0042700 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
//        6;7;8;9;10;11;12;
//        2018-03-22T00:02:46.916+0800: [GC (Allocation Failure) [PSYoungGen: 7886K->560K(9216K)] 7886K->568K(29696K), 0.0023456 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
//        13;14;15;16;17;18;19;
//        Heap
//        PSYoungGen      total 9216K, used 8134K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
//        eden space 8192K, 92% used [0x00000000ff600000,0x00000000ffd65908,0x00000000ffe00000)
//        from space 1024K, 54% used [0x00000000fff00000,0x00000000fff8c010,0x0000000100000000)
//        to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
//        ParOldGen       total 20480K, used 8K [0x00000000fe200000, 0x00000000ff600000, 0x00000000ff600000)
//        object space 20480K, 0% used [0x00000000fe200000,0x00000000fe202000,0x00000000ff600000)
//        Metaspace       used 3077K, capacity 4496K, committed 4864K, reserved 1056768K
//        class space    used 338K, capacity 388K, committed 512K, reserved 1048576K


    }

}
