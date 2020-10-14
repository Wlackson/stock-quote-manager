package com.inatel.stockquotemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTasksService {

    private final RestClientService restClientService;
    private final ScheduledAnnotationBeanPostProcessor taskScheduler;
    private static final int MAX_CALLS_TO_STOCKMANAGER = 100;
    private int counting = 0;

    @SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection"})
    @Autowired
    public ScheduledTasksService(RestClientService restClientService, ScheduledAnnotationBeanPostProcessor taskScheduler) {
        this.restClientService = restClientService;
        this.taskScheduler = taskScheduler;
    }

    @Scheduled(initialDelay = 10000L, fixedDelay = 5000L)
    public void callStockManagerSubscriptionService() {

        if ((restClientService.subscribeToStockChanges())
                || (counting > MAX_CALLS_TO_STOCKMANAGER)) {

            taskScheduler.destroy();
            Thread.currentThread().interrupt();

        }

        counting++;

    }

}
