package com.ynthm.demo.jdk8.concurrent.task;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author Ethan Wang
 */
@Data
@Slf4j
public class ScheduleTask implements Runnable {

  private String id;

  public ScheduleTask(String id) {
    this.id = id;
  }

  @Override
  public void run() {
    System.out.println(
        Thread.currentThread().getName()
            + " name: "
            + id
            + " time: "
            + Instant.now().getEpochSecond());
  }

  public static void main(String[] args) throws InterruptedException {
    log.info("当前时间" + LocalDateTime.now());
    ScheduleUtil.start(new ScheduleTask("task1"), 2);
    TimeUnit.SECONDS.sleep(2);
    ScheduleUtil.reset(new ScheduleTask("task1"), 1);
    TimeUnit.SECONDS.sleep(2);
    ScheduleUtil.cancel(new ScheduleTask("task1"));
    TimeUnit.SECONDS.sleep(2);
  }
}
