package com.cliffyuan.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cliffyuan on 14-7-14.
 */
public class AbstractQueuedSynchronizerTest {

    private ReentrantLock lock=new ReentrantLock();

    private Condition condition=lock.newCondition();

    public static void main(String[] args) {

        AbstractQueuedSynchronizerTest test=new AbstractQueuedSynchronizerTest();
        Thread thread=new Thread(new Work(test));
        thread.start();

        thread=new Thread(new Work(test));
        thread.start();
    }

    public void testLock(){
        try{
            System.out.println("lock start");

            lock.lock();

            System.out.println("exe testLock");

            lock.isHeldByCurrentThread();

            condition.await();
            condition.signalAll();

            Thread.sleep(1000000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
            System.out.println("unlock");
        }
    }

}

class Work implements Runnable{

    AbstractQueuedSynchronizerTest test;

    Work(AbstractQueuedSynchronizerTest test) {
        this.test = test;
    }

    @Override
    public void run() {
        test.testLock();
    }
}
