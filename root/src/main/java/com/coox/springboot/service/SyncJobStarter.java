package com.coox.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SyncJobStarter {

    private final AsyncExecutorService service;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void registerAuditors() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            System.out.printf("Starting service with id %d\n", i);
            service.execute(i);
            System.out.printf("Finished service with id %d\n", i);
        }
    }
}
