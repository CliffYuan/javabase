package com.cliffyuan.gc.base;

/**
 * 根搜索算法不可到达的对象也并非是“非死不可”的。真正宣告一个对象死亡，至少要经历两次标记过程：
 * 1.第一次标记（附加一次是否执行finalize方法）：如果对象在根搜索后发现没有与GC Roots相关联的引用链，它将会被进行第一次的标记。
 *           并且进行一次帅选，帅选的条件是此对象是否有必要执行finalize方法。
 *           a.当对象没有覆盖finalize方法或者finalize方法已经被虚拟机调用过，则没有必要执行finalize方法。
 *           b.当对象有必要执行finalize方法时，那么这个对象会被放置在一个F-Queue的队列中，并稍后由JVM Finalizer线程去执行（只是触发这个方法，不一定执行完成）。
 * 2.第二次标记（针对F-Queue队列中的对象）：GC对F-Queuez中的对象进行第二次小规模标记（从根搜索发现是否有GC Roots相关联的引用链），
 *           a.如果在finalize方法中有重新建立关联了，则将对象从F-Queue队列中删除。
 *           b.否则离死不远了
 *
 *
 * finalize方法总结：
 * 1）对象的finalize方法只会执行一次。
 * 2）并不保证finalize方法执行完成，只是触发该方法。
 * 3）JVM Finalizer 线程执行该方法。
 *
 *
 * 下面例子执行结果：
 * output:[
 * FinalizeTest.finalize method executed!
 * yes,i am still alive.
 * --------------第二次---------------
 * no,i am dead.
 * ]
 *
 * Created by xnd on 2016/2/27.
 */
public class FinalizeTest {

    public static FinalizeTest SAVE_HOOK=null;

    public void isAlive(){
        System.out.println("yes,i am still alive.");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("FinalizeTest.finalize method executed!");
        FinalizeTest.SAVE_HOOK=this;
    }

    public static void main(String[] args)throws InterruptedException {
        //创建实例
        SAVE_HOOK=new FinalizeTest();
        //制空
        SAVE_HOOK=null;
        //手动触发垃圾回收
        System.gc();
        Thread.sleep(500);
        if(SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no,i am dead.");
        }

        System.out.println("--------------第二次---------------");
        //刚刚创建的对象引用还在，是存活的

        //制空
        SAVE_HOOK=null;
        //手动触发垃圾回收
        System.gc();
        Thread.sleep(500);
        if(SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no,i am dead.");
        }
    }
}


