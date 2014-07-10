package com.cliffyuan.thread;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by cliffyuan on 14-6-23.
 */
public class ThreadInterruptTest {

    public static void main(String[] args) throws Exception{
        Work work=new Work();
        work.start();
        Thread.sleep(5000);
        System.out.println("["+Thread.currentThread().getName()+"]中断线程,状态="+work.isInterrupted());
        work.interrupt();
        System.out.println("["+Thread.currentThread().getName()+"]中断线程,状态="+work.isInterrupted());


    }



}

class Work extends Thread{

    @Override
    public void run() {
        try{
            System.out.println("["+Thread.currentThread().getName()+"]等待读取信息,"+this.isInterrupted());
            try{
                System.in.read();
            }catch (IOException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("["+Thread.currentThread().getName()+"]调用Thread.interrupted()前,"+this.isInterrupted());

        Thread.interrupted();

        System.out.println("["+Thread.currentThread().getName()+"]调用Thread.interrupted()后,"+this.isInterrupted());
    }
}


