package com.framework.threadDemo;

import java.util.UUID;
import java.util.concurrent.*;

public class ThreadPoolDemo extends ThreadPoolExecutor{
    private String name;

    private int maxSize;

    private int coreSize;

    private BlockingQueue<Runnable> taskQueue;


    public ThreadPoolDemo(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadPoolDemo(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPoolDemo(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPoolDemo(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    /*@Override
    public void execute(Runnable command) {
// 正在运行的线程数
        int count = runningCount.get();
        // 如果正在运行的线程数小于核心线程数，直接加一个线程
        if (count < coreSize) {
            // 注意，这里不一定添加成功，addWorker()方法里面还要判断一次是不是确实小
            if (addWorker(task, true)) {
                return;
            }
            // 如果添加核心线程失败，进入下面的逻辑
        }

        // 如果达到了核心线程数，先尝试让任务入队
        // 这里之所以使用offer()，是因为如果队列满了offer()会立即返回false
        if (taskQueue.offer(task)) {
            // do nothing，为了逻辑清晰这里留个空if

        } else {
            // 如果入队失败，说明队列满了，那就添加一个非核心线程
            if (!addWorker(task, false)) {
                // 如果添加非核心线程失败了，那就执行拒绝策略
                rejectPolicy.reject(task, this);
            }
        }
    }*/



    public static void main(String[] args) {
        // LinkedBlockingQueue
        // ArraysBlockingQueue
        //
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

        // AbortPolicy          抛出异常,丢弃任务
        // DiscardPolicy        不抛出异常，丢弃任务
        // DiscardOldestPolicy  不抛出异常，丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
        // CallerRunsPolicy     调用线程处理该任务
        executor.setRejectedExecutionHandler(new DiscardOldestPolicy());
        executor.setThreadFactory(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("Work-" + UUID.randomUUID().toString());
                return t;
            }
        });

        for(int i = 0; i < 50; i++) {
            String str = String.valueOf(i);
            executor.execute(() -> {
                System.out.println("ss" + str);
                try {
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }



        while (true) {
            System.out.println("getActiveCount:"+executor.getActiveCount());// = 正在运行的coresize + queuesize
            System.out.println("getQueueSize:"+executor.getQueue().size());
            System.out.println("getCoreSize:"+executor.getCorePoolSize());
            System.out.println("getMaxSize:"+executor.getMaximumPoolSize());
            System.out.println("=======================================");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
