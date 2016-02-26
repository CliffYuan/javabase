package com.cliffyuan.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 使用Unsafe修改属性的值，反射有时候做不到的
 * <p/>
 * Created by xiaoniudu on 16-2-26.
 */
public class UnsafeModifyValue {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Guard guard = new Guard();
        System.out.println(guard.giveAccess());

        //获取Unsafe
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);

        //使用Unsafe修改对象的值，（反射也可用做到）
        Field fieldAccessAllowed = Guard.class.getDeclaredField("accessAllowed");
        unsafe.putInt(guard,unsafe.objectFieldOffset(fieldAccessAllowed),42);//设置值
        System.out.println(guard.giveAccess());

        System.out.println("修改没有引用对象的值--------------");
        //修改对象guard2的值，反射做不到了这次（但值得关注的是，我们可以修改任何对象，甚至没有这些对象的引用）
        Guard guard1=new Guard();
        Guard guard2=new Guard();
        System.out.println("修改前："+guard2.giveAccess());
        unsafe.putInt(guard1,unsafe.objectFieldOffset(fieldAccessAllowed)+16,42);//注意：我们不必持有这个对象的引用。16是Guard对象在32位架构上的大小
        System.out.println("修改后："+guard2.giveAccess());
    }
}


class Guard {

    private int accessAllowed = 1;

    public boolean giveAccess() {
        return 42 == accessAllowed;
    }
}