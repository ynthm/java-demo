package com.ynthm.jdk.concurrent.task;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** @author Ynthm Wang */
public class ScheduleUtil {
  private static Map<String, ScheduledFuture<?>> taskMap = new HashMap<>();
  private static ScheduledExecutorService service =
      new ScheduledThreadPoolExecutor(
          2,
          new BasicThreadFactory.Builder()
              .namingPattern("example-schedule-pool-%d")
              .daemon(true)
              .build());

  public static void start(ScheduleTask task, long period) {
    ScheduledFuture<?> scheduledFuture =
        service.scheduleAtFixedRate(task, 0, period, TimeUnit.SECONDS);

    taskMap.put(task.getId(), scheduledFuture);
  }

  /**
   * @param task
   * @param period
   */
  public static void startStopWatch(ScheduleTask task, long period) {
    ScheduledFuture<?> scheduledFuture = service.schedule(task, period, TimeUnit.SECONDS);
    taskMap.put(task.getId(), scheduledFuture);
  }

  public static void cancel(ScheduleTask task) {
    ScheduledFuture<?> scheduledFuture = taskMap.get(task.getId());
    if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
      scheduledFuture.cancel(false);
    }

    taskMap.remove(task.getId());
  }

  public static void reset(ScheduleTask task, long period) {
    // 先取消任务
    cancel(task);
    start(task, period);
  }
}
