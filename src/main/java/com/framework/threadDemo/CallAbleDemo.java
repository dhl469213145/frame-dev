package com.framework.threadDemo;

import java.util.concurrent.*;

public class CallAbleDemo implements Callable {
    @Override
    public String call() throws Exception {
        System.out.println("call method is start....");
        TimeUnit.SECONDS.sleep(3);
        System.out.println("call method is finish....");
        return "aaa";
    }


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));
        Future<String> future = executor.submit(new CallAbleDemo());
        try {
            String reStr = future.get();
            System.out.println(reStr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
