package com.coox.springboot.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AsyncExecutorService {

    private static final Set<Integer> IDS = ConcurrentHashMap.newKeySet();

    @Async
    public void execute(Integer id) throws InterruptedException {
        Thread.sleep(100);
        System.out.printf("Job with %d accepted\n", id);
        internal(id);
        System.out.printf("Job with %d finished\n", id);
        System.out.println("\n" + IDS + "\n");
    }

    private synchronized void internal(Integer id) throws InterruptedException {
        IDS.add(id);
        System.out.printf("Inside sync method with id %d\n", id);
        Thread.sleep(1000);
        System.out.printf("Sync call with %d completed\n", id);
    }

}
