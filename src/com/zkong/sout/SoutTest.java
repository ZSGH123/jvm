package com.zkong.sout;

/**
 * 一：System.ou.println()
 *  1.Jvm虚拟机中有个锁优化原则之一就是“锁粗化”，何为锁粗化，但是如果一系列的连续操作都对同一个对象反 复加锁和解锁，
 *  甚至加锁操作是出现在循环体中的，那即使没有线程竞争，频繁地进行互斥 同步操作也会导致不必要的性能损耗
 *  所以加了System.ou.println()之后相当于代码变成了：
 *  synchronized (obj) {
 *     for (int i = 0; i < 10000; i++) {
 *         increase();
 *     }
 * }
 *
 */
public class SoutTest {

    public static volatile int count = 0;

    public static void increase() {
        count++;
       //System.out.println(count);
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];

        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (threads) {
                        for (int i = 0; i < 10000; i++) {
                            increase();
                        }
                    }
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("count"+count);
    }

}
