package com.zkong.future;

import com.zkong.sout.CoolWater;

import java.util.Timer;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JdkThreadPoolExecutorTest {

    /**
     * 线程池的提交方式:
     * 1.先是最简单的，execute 方式提交，不关心返回值的，直接往线程池里面扔任务就完事
     * 2.有三种 submit。这三种按照提交任务的类型来算分为两个类型。
     *    提交执行 Runnable 类型的任务。
     *      1.public <T> Future<T> submit(Runnable task, T result)
     *        此方法扔进去一个 Runable 的任务的同时，再扔进去一个泛型 T ，
     *        而巧好返回的 Future 里面的泛型也是 T，那么我们大胆的猜测一下这就是同一个对象。
     *        如果是同一个对象，说明我们可以一个对象传到任务体里面去一顿操作，
     *        然后通过 Future 再次拿到这个对象的
     *
     *      2.public Future<?> submit(Runnable task)
     *        此方法扔进去一个 Runable 的任务，返回一个 Future，而这个返回的 Future ，
     *        相当于是返回了一个寂寞
     *
     *    提交执行 Callable 类型的任务。
     *     1. public <T> Future<T> submit(Callable<T> task)
     *
     *    好了。综上，线程池的提交方式一共有四种：一种 execute，无返回值。三种 submit，有返回值。
     *    submit 中按照提交任务的类型又分为两种：一个是 Callable，一个是 Runable。
     *    submit 中 Runable 的任务类型又有两个重载方法：一个返回了个寂寞，一个返回了个渣男。
     *    哦，不。一个返回了个寂寞，一个返回了个对象。
     *    这个时候就有人要站出来说：你说的不对，你就是瞎说，明明就只有 execute 这一种提交方式。
     *    是的，“只有 execute 这一种提交方式”这一种说法也是没错的。
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
                2,4,
                6,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(1024));

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                System.out.println("execute ");
            }
        });
        System.out.println("hello  ");
//        threadPoolExecutor.shutdown();


       Future<?> future= threadPoolExecutor.submit(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello 111 ");
        });

        System.out.println("hello 222 "+future.get());
       // Thread.currentThread().join();

        CoolWater coolWater=new CoolWater();
        Future<CoolWater> coolWaterFuture= threadPoolExecutor.submit(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello 111 ");
        },coolWater);
        System.out.println("hello 222 "+coolWaterFuture.get());

        Future<String> res=threadPoolExecutor.submit(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello 333 ");
            return "4141";
        });
        System.out.println("hello 444=== "+res.isDone());

        System.out.println("hello 555=== "+res.get());

        threadPoolExecutor.shutdown();
    }


}
