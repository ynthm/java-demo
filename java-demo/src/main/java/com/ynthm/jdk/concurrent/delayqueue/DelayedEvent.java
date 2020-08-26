package com.ynthm.jdk.concurrent.delayqueue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * A DelayQueue is an unbounded blocking queue of Delayed elements.
 */
public class DelayedEvent implements Delayed {
    private long id;
    private String name;
    private LocalDateTime activationDateTime;

    public DelayedEvent(long id, String name, LocalDateTime activationDateTime) {
        super();
        this.id = id;
        this.name = name;
        this.activationDateTime = activationDateTime;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getActivationDateTime() {
        return activationDateTime;
    }

    @Override
    public int compareTo(Delayed that) {
        long result = this.getDelay(TimeUnit.NANOSECONDS)
                - that.getDelay(TimeUnit.NANOSECONDS);
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        LocalDateTime now = LocalDateTime.now();
        long diff = now.until(activationDateTime, ChronoUnit.MILLIS);
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public String toString() {
        return "DelayedEvent [id=" + id + ", name=" + name + ", activationDateTime=" + activationDateTime + "]";
    }
}