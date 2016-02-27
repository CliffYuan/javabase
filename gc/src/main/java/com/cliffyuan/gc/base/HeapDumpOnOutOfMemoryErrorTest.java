package com.cliffyuan.gc.base;

import java.util.ArrayList;
import java.util.List;

/**
 * vm args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *
 * 1）执行时，会抛出如下的错误（并且生成java_pid8884.hprof堆文件）：
 *    output:[
 *    java.lang.OutOfMemoryError: Java heap space
 *    Dumping heap to java_pid8884.hprof ...
 *    Heap dump file created [28129540 bytes in 0.106 secs]
 *    ]
 * 2）如果没有设置-XX:+HeapDumpOnOutOfMemoryError,则抛出如下错误（没有生成堆文件）
 *    output:[
 *    Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 *    ]
 *
 * Created by xnd on 2016/2/27.
 */
public class HeapDumpOnOutOfMemoryErrorTest {

    public static void main(String[] args) {
        List<Object> list=new ArrayList<Object>();
        while (true){
            list.add(new Object());
        }
    }
}
