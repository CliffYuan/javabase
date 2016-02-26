package com.cliffyuan.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by xiaoniudu on 16-2-26.
 */
public class UnsafeMemory {

    static Unsafe UNSAFE;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe"); // Internal reference
        f.setAccessible(true);
        UNSAFE = (Unsafe) f.get(null);


        System.out.println("memory result:" + memory());
    }

    public static long memory() {
        long address = UNSAFE.allocateMemory(8);//分配内存
        long address_int = UNSAFE.allocateMemory(4);//分配内存
        long address_byte = UNSAFE.allocateMemory(1);//分配内存   相隔32的
        System.out.println("address:" + address);
        System.out.println("address:" + address_int);
        System.out.println("address:" + address_byte);
        UNSAFE.putLong(address, 100L);

        System.out.println("addresssize:"+UNSAFE.ADDRESS_SIZE);
        System.out.println("addresssize:"+UNSAFE.addressSize());
        System.out.println("pagesize:"+UNSAFE.pageSize());

        return UNSAFE.getAddress(address);
    }

}
