package com.zhuangjie.gulimall.search;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class AsynStu {
    /**
     * 学习异步
     * 1、
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         *                               int corePoolSize,
         *                               int maximumPoolSize,
         *                               long keepAliveTime,
         *                               TimeUnit unit,
         *                               BlockingQueue<Runnable> workQueue,
         *                               ThreadFactory threadFactory,
         *                               RejectedExecutionHandler handle
         */
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                10, 20,
                30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()

        );

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1执行");
            return 1314;
        }, threadPool);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("任务2开始返回");
            return 13142;
        }, threadPool);
        System.out.println("any="+CompletableFuture.anyOf(future1, future2).get());
        System.out.println("allof,1=>"+future1.get()+",2=>"+future2.get());


    }


}
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("方式二");
    }
}