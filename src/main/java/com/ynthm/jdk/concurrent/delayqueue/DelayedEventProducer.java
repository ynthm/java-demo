package com.ynthm.jdk.concurrent.delayqueue;

import java.time.LocalDateTime;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DelayedEventProducer implements Runnable {
    private final DelayQueue<DelayedEvent> queue;
    private AtomicInteger counter;

    public DelayedEventProducer(DelayQueue<DelayedEvent> queue, AtomicInteger counter) {
        this.queue = queue;
        this.counter = counter;
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        int id = counter.incrementAndGet();
        DelayedEvent event = new DelayedEvent(id, "Task-" + id, now);
        System.out.println("Added to queue :: " + event);
        queue.add(event);
    }
}