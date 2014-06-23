package com.cliffyuan.lock;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.线程消亡不会释放锁,必须手动释放
 * 2.
 *
 * Created by yuanyuanming on 14-6-23.
 */
public class ReentrantLockTest {

    RT lock = new RT();

    CountDownLatch countDownLatch=new CountDownLatch(1);

    CountDownLatch end=new CountDownLatch(2);

    /**
     * 获取锁多次
     */
    @Test
    public void testTryLock()throws InterruptedException{
        Work work=new Work(false);
        Thread thread=new Thread(work);
        thread.start();

        Work work2=new Work(true);
        thread=new Thread(work2);
        thread.start();


        end.await();

    }

    class Work implements Runnable {

        private boolean flag;

        Work(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            try {
                if(flag){
                    System.out.println("线程["+Thread.currentThread().getName()+"]等待中");
                    countDownLatch.await();
                }
                retryTryLock();
                System.out.println("线程["+Thread.currentThread().getName()+"]睡眠中");
                Thread.sleep(10000);

                System.out.println("线程["+Thread.currentThread().getName()+"]结束");
                end.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int count=lock.getHoldCount();
            for(int i=0;i<count;i++){
               //  lock.unlock(); //在线程消亡前，再次释放锁，锁才能真的被释放
            }

            System.out.println("线程["+Thread.currentThread().getName()+"]消亡,hold thread="+lock.getHoldThread());
        }

        public void retryTryLock() throws InterruptedException{
            try {
                System.out.println("线程["+Thread.currentThread().getName()+"]第一次获取锁开始,hold thread="+lock.getHoldThread());
                //第一次获取锁
                boolean b = lock.tryLock();
                System.out.println("查询["+Thread.currentThread().getName()+"]线程保持此锁的次数:"+lock.getHoldCount()+",tryLock="+b);
                System.out.println("线程["+Thread.currentThread().getName()+"]第二次获取锁开始,hold thread="+lock.getHoldThread());
                //第二次获取锁
                lock.lock();//没有获取到锁，会阻塞
                System.out.println("查询[" + Thread.currentThread().getName() + "]线程保持此锁的次数:" + lock.getHoldCount() + ",hold thread=" + lock.getHoldThread());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw e;
            } finally {
                //释放一次锁
                lock.unlock();

                countDownLatch.countDown();
                System.out.println("线程["+Thread.currentThread().getName()+"]第一次释放锁结束,hold thread="+lock.getHoldThread());
            }
        }
    }

    class RT extends ReentrantLock{
        /**
         * 当前获得锁的线程
         * @return
         */
        public Thread getHoldThread(){
            return super.getOwner();
        }
    }
}
