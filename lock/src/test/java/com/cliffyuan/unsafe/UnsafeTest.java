package com.cliffyuan.unsafe;

import junit.framework.Assert;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by yuanyuanming on 14-7-1.
 */
public class UnsafeTest {

    /**
     * (1)避免初始化
     当你想要跳过对象初始化阶段，或绕过构造器的安全检查，
     或实例化一个没有任何公共构造器的类，
     allocateInstance方法是非常有用的。
     * @throws Exception
     */
    @Test
    public void testInit()throws Exception{
        Field field= Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe=(Unsafe)field.get(null);
        A a=new A();
        Assert.assertEquals(3,a.getI());

        A a1=(A)unsafe.allocateInstance(A.class);
        Assert.assertEquals(0,a1.getI());

    }

    class A{

        private int i;

        public A(){
            this.i=3;
        }

        public int getI() {
            return i;
        }
    }
}
