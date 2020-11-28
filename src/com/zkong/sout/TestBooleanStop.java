package com.zkong.sout;

/**
 * 二：System.ou.println()
 * 2.主内存与工作内存的“小桥梁“
 * 他每次输出都会清除工作内存去同步主内存，由于我们修改的flag存在在主内存，
 * 所以每次输出去同步的话必然会把flag更新到我们的工作内存当中并且停止了程序运行
 * 获得同步锁；
 * 1、清空工作内存；
 * 2、从主内存拷贝对象副本到工作内存；
 * 3、执行代码(计算或者输出等)；
 * 4、刷新主内存数据；
 * 5、释放同步锁。
 *
 */
public class TestBooleanStop implements Runnable{

    public  boolean flag=true;

    @Override
    public void run() {
        while(flag){
            System.out.println("是否退出："+flag);
        }
        System.out.println("退出");
    }
    public static void main(String[] args) throws Exception {
        TestBooleanStop testBooleanStop = new TestBooleanStop();
        Thread t=new Thread(testBooleanStop);
        t.start();
        Thread.sleep(1000);
        testBooleanStop.flag=false;
        Thread.sleep(1000);
        System.out.println(testBooleanStop.flag);

    }

}
