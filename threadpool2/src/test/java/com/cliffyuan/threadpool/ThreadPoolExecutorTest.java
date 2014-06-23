package com.cliffyuan.threadpool;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;

/**
 *
 * Created by cliffyuan on 14-6-21.
 */
public class ThreadPoolExecutorTest {

    ThreadPoolExecutor executor;

    ThreadPoolExecutor executorSeq;

    @Before
    public void before(){
        executor=new ThreadPoolExecutor(2,4,
                6, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                new ThreadPoolExecutor.CallerRunsPolicy());

        executorSeq=new ThreadPoolExecutor(2,4,
                6, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new ThreadPoolExecutor.CallerRunsPolicy());

    }

    @Test
    public void add2CallRun(){
        for (int i=1;i<20;i++){
            System.out.println("当前executor情况前，index="+i+"，activecount="+executor.getActiveCount()+",poolsize="+executor.getPoolSize()+",queueSize="+executor.getQueue().size());
            executor.execute(new Woker("" + i));
            System.out.println("当前executor情况后，index="+i+"，activecount="+executor.getActiveCount()+",poolsize="+executor.getPoolSize()+",queueSize="+executor.getQueue().size());
        }
    }

    @Test
    public void testSynchronous(){
        for (int i=1;i<20;i++){
            if(i==3){
                System.out.println("3");
            }
            System.out.println("当前executor情况前，index="+i+"，activecount="+executorSeq.getActiveCount()+",poolsize="+executorSeq.getPoolSize()+",queueSize="+executorSeq.getQueue().size());
            executorSeq.execute(new Woker("" + i));
            System.out.println("当前executor情况后，index="+i+"，activecount="+executorSeq.getActiveCount()+",poolsize="+executorSeq.getPoolSize()+",queueSize="+executorSeq.getQueue().size());
        }
    }

    class Woker implements Runnable{
        String name=null;

        Woker(String n) {
            name=n;
        }

        @Override
        public void run() {
            try{
                System.out.println("任务执行"+Thread.currentThread().getName()+"-start"+"-"+name);
               // this.wait(10000);
                Thread.sleep(20000);
                System.out.println("任务执行" + Thread.currentThread().getName() + "-end" + "-" + name);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
