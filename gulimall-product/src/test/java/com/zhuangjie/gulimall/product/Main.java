package com.zhuangjie.gulimall.product;

import java.util.concurrent.Semaphore;

public class Main {
    private static int resource = 100;
    private static PV pv = new PV();
    private Semaphore sem = new Semaphore(1);
    public static void main(String[] args) {




        new Thread(()->{
            while (true) {
                try {
                    pv.acquire();
                    System.out.println(Thread.currentThread().getName()+ "访问临界资源："+(resource++));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    pv.release();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        },"A线程").start();
        new Thread(()->{
            while (true) {
                try {
                    pv.acquire();
                    System.out.println(Thread.currentThread().getName()+ "访问临界资源："+(resource++));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    pv.release();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        },"B线程").start();
    }
}
