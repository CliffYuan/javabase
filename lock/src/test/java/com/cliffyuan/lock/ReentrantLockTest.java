package com.cliffyuan.lock;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.线程消亡不会释放锁,必须手动释放
 * 2.已经获取锁的线程，可以多次lock(),释放时也必须多次。
 * 3.tryLock立即返回，lock阻塞，LockInterruptibly阻塞（响应中断）
 * 4.调用unlock方法时，必须持有锁
 * Created by yuanyuanming on 14-6-23.
 */
public class ReentrantLockTest {

    RT lock = new RT();

    CountDownLatch countDownLatch = new CountDownLatch(1);

    CountDownLatch end = new CountDownLatch(2);

    /**
     * 获取锁多次
     */
    @Test
    public void testTryLock() throws InterruptedException {
        Work work = new Work(false);
        Thread thread = new Thread(work);
        thread.start();

        Work work2 = new Work(true);
        thread = new Thread(work2);
        thread.start();
        end.await();
    }

    /**
     * 测试锁相应中断
     * <p/>
     * （1）如果锁被另一个线程保持，则出于线程调度目的，禁用当前线程，并且在发生以下两种情况之一以前，
     * 该线程将一直处于休眠状态： 锁由当前线程获得；或者其他某个线程中断当前线程。
     * <p/>
     * （2）如果当前线程：在进入此方法时已经设置了该线程的中断状态；或者在等待获取锁的同时被中断。
     * 则抛出 InterruptedException，并且清除当前线程的已中断状态。
     */
    @Test
    public void testLockInterruptibly() {
        WorkLock workLock = new WorkLock();
        Thread thread = new Thread(workLock);
        thread.start();

        WorkLock workLock2 = new WorkLock();
        thread = new Thread(workLock2);
        thread.start();
        try {
            Thread.sleep(1000);
            System.out.println("中断线程开始");
            thread.interrupt();
            end.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class WorkLock implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("线程[" + Thread.currentThread().getName() + "]获取锁");
                lock.lockInterruptibly();
                try {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]睡眠中");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]睡眠中被中断");
                }
            } catch (InterruptedException e) {
                System.out.println("线程[" + Thread.currentThread().getName() + "]被中断");
            } finally {
                //调用unlock方法时，必须持有锁
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
            System.out.println("线程[" + Thread.currentThread().getName() + "]消亡");
            end.countDown();
        }
    }

    class Work implements Runnable {

        private boolean flag;

        Work(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            try {
                if (flag) {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]等待中");
                    countDownLatch.await();
                }
                retryTryLock();
                System.out.println("线程[" + Thread.currentThread().getName() + "]睡眠中");
                Thread.sleep(10000);

                System.out.println("线程[" + Thread.currentThread().getName() + "]结束");
                end.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int count = lock.getHoldCount();
            for (int i = 0; i < count; i++) {
                //  lock.unlock(); //在线程消亡前，如果该线程还占有锁，需要再次释放锁，锁才能真的被释放
            }

            System.out.println("线程[" + Thread.currentThread().getName() + "]消亡,hold thread=" + lock.getHoldThread());
        }

        public void retryTryLock() throws InterruptedException {
            try {
                System.out.println("线程[" + Thread.currentThread().getName() + "]第一次获取锁开始,hold thread=" + lock.getHoldThread());
                //第一次获取锁
                boolean b = lock.tryLock();
                System.out.println("查询[" + Thread.currentThread().getName() + "]线程保持此锁的次数:" + lock.getHoldCount() + ",tryLock=" + b);
                System.out.println("线程[" + Thread.currentThread().getName() + "]第二次获取锁开始,hold thread=" + lock.getHoldThread());
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
                System.out.println("线程[" + Thread.currentThread().getName() + "]第一次释放锁结束,hold thread=" + lock.getHoldThread());
            }
        }
    }

    class RT extends ReentrantLock {
        /**
         * 当前获得锁的线程
         *
         * @return
         */
        public Thread getHoldThread() {
            return super.getOwner();
        }
    }
}
