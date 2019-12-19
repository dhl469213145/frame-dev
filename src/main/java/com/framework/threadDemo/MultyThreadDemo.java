package com.framework.threadDemo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultyThreadDemo{

    public void run(){
        System.out.println("--------");
    }

    public static void main(String[] args) {
//        MultyThreadDemo multyThreadDemo = new MultyThreadDemo(new IndexRunnable());
        IndexRunnable indexRunnable = new IndexRunnable();
        for(int i = 0; i < 100000; i++) {
            new Thread(indexRunnable).start();
        }

        System.out.println("======"  + indexRunnable.getCountDown());

    }



}

class IndexRunnable implements Runnable{
//    private AtomicInteger countDown = new AtomicInteger(100000);
    private int countDown = 100000;
    private Lock lock = new ReentrantLock();
    private AtomicStampedReference<Integer> kReference = new AtomicStampedReference<>(10,0);

    public int getCountDown() {
        return countDown;
//        return countDown.get();
    }



    @Override
    public void run() {
//        System.out.println("++++++++" + countDown.decrementAndGet());
        /*synchronized (this) {
            System.out.println("++++++++" + countDown--);
        }*/


//        kReference.
        lock.lock();
        System.out.println("++++++++" + countDown--);
        lock.unlock();

       /* try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}