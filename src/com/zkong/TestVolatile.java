package com.zkong;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * -server -Xcomp -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssmbly -XX:CompileCommand=compileonly,*VolatileVisibilityTest.prepareData
 * 一：八大数据原子操作
 * 1.read：从主内存读取数据
 * 2.load：将主内数据写入到工作内存
 * 3.use：读取工作内存中数据做计算
 * 4.assign：将计算好的值重新赋值到工作内存
 * 5.store：将工作内存的数据写入主内存
 * 6.write：将store过去的变量值赋值给主内存中的变量
 * 7.lock：将主内存中的变量加锁，标志为线程独占状态
 * 8.unlock：将主内存中的变量解锁，解锁后其他线程可以锁定该变量
 * 总线加锁：
 cpu从主内存读取数据到高速缓存，会在总线对这个数据加锁，这样其他cpu无法读取或写这个数据，直到这个cpu使用完数据释放锁，才能读取该数据
 * 二：volatile关键字保证可见性，顺序性，不保证原子性
 *   如何保证可见性：
 *   1.缓存一致性协议
 *   2.A：底层原理实现主要通过汇编语言中的lock前缀指令，他会锁定这块内存区域的缓存（cpu缓存行锁定 ），并写回到主内存
 *   B:IA-32架构软件开发者手册对lock指令的解释：
 *     1】会将当前处理器缓存行的数据立即写回到系统内存
 *     2】这个写回内存的操作会经过cpu总线，触发（mesI缓存一致性协议）cpu总线嗅探机制，使得其他线程（cpu）中工作内存缓存了该内存地址的数据无效。
 *   C:volatile关键字保正可见性，有序性(防止cpu对程序指令执行顺序的优化，加入内存屏障)，但是不保证原子性
 *
 *
 */
public class TestVolatile {

    public volatile static Boolean isSucces=false;

    public  static int a=0;


    static class Task implements Runnable {

        @Override
        public void run() {
            load();
        }

        public  void load(){
            while (true){
               // System.out.println("载入中。。。"+a/100.0+"%");
                if(a>9999) {
                    System.out.println("载入成功"+a);
                    break;
                }
            }
        }
    }

    public static void main(String[] strings) {

        new Thread(new Task()).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10000;i++){
                    a++;
                }

//                isSucces=true;
//                System.out.println(isSucces);
                System.out.println(a);
            }
        }
        ).start();

    }
}
