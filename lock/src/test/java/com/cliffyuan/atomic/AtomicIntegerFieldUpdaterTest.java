package com.cliffyuan.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by yuanyuanming on 14-6-27.
 */
public class AtomicIntegerFieldUpdaterTest {


    @Test
    public void test(){
        AtomicIntegerFieldUpdater<Person> a= AtomicIntegerFieldUpdater.newUpdater(Person.class,"id");
        Person person=new Person(0,"xnd");

        Work w1=new Work(a,person);
        w1.start();
        w1=new Work(a,person);

        w1.start();

        System.out.println(person.getId());
    }


    class Person{

        public volatile int id;

        private String name;

        Person(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Work extends Thread{

        AtomicIntegerFieldUpdater<Person> a;

        Person c;

        public Work(AtomicIntegerFieldUpdater<Person> b,Person c1){
            this.a=b;
            c=c1;
        }

        @Override
        public void run() {
           for(int i=0;i<10;i++){
               a.addAndGet(c,i);
           }
        }
    }
}
