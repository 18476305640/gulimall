package com.zhuangjie.gulimall.product;


import java.util.*;
public class PV {
    private volatile int value = 1; // 下个要取的量
    private Queue<Thread> queue = new LinkedList<>();

    public synchronized void acquire() throws InterruptedException {
        Thread thread = Thread.currentThread();
        if (--value < 0) {
            queue.add(thread);
            synchronized (thread) {
                thread.wait();
            }
        }
    }
    public void release() {
        value++;
        Thread thread = queue.poll();
        if (thread != null) {
            synchronized (thread) {
                thread.notify();
            }
        }
    }
}
