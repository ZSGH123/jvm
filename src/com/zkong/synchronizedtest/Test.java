package com.zkong.synchronizedtest;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    public static AtomicInteger a = new AtomicInteger(0);

    public static Integer b =0;

    static class Task implements Runnable {

        @Override
        public void run() {
           add();
        }
        //不要丢掉static，否则不能保证高并发性
        /**
         * synchronized是Java中的关键字，是一种同步锁。它修饰的对象有以下几种：
         * synchronized修饰不加static的方法，锁是加在单个对象上，不同的对象没有竞争关系；
         * 修饰加了static的方法，锁是加载类上，这个类所有的对象竞争一把锁
         * 1. 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象；
         * 2. 修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象；
         * 3. 修改一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象；
         * 4. 修改一个类，其作用的范围是synchronized后面括号括起来的部分，作用主的对象是这个类的所有对象。
         */
        public synchronized static void add(){
            for (int i = 0; i < 100000; i++) {
                b++;
            }
            System.out.println("b=" + b);
        }
    }


    public static void main(String[] strings) {

        new Thread(new Task()).start();
        new Thread(new Task()).start();
        new Thread(new Task()).start();
        new Thread(new Task()).start();
        new Thread(new Task()).start();

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
