package com.ynthm.demo.jdk8.concurrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class CompletableFutureTest {
  /** 线程池大小 */
  private static final int CORE_POOL_SIZE = 4;

  private static final int MAX_POOL_SIZE = 12;
  private static final long KEEP_ALIVE_TIME = 5L;

  /** 总共要处理的数据 */
  private static final int TOTAL = 100;

  /** 每个线程处理的数据 */
  private static final int SIZE = 10;

  private static final int QUEUE_SIZE = (int) (TOTAL / SIZE);

  protected static final ExecutorService THREAD_POOL =
      new ThreadPoolExecutor(
          CORE_POOL_SIZE,
          MAX_POOL_SIZE,
          KEEP_ALIVE_TIME,
          TimeUnit.SECONDS,
          new LinkedBlockingQueue<>(QUEUE_SIZE));

  public static void main(String[] args) throws Exception {

    int index = 1;
    List<CompletableFuture<Map<String, Integer>>> futures = new ArrayList<>();

    int pages = TOTAL / (SIZE * CORE_POOL_SIZE) + 1;
    for (int i = 0; i < pages; i++) {
      for (int j = 0; j < CORE_POOL_SIZE; j++) {
        if (index > TOTAL) {
          continue;
        }
        // TimeUnit.MICROSECONDS.sleep(new Random().nextInt(1000) + 1);
        futures.add(CompletableFuture.supplyAsync(new AnalysisTask(index, SIZE), THREAD_POOL));
        index += SIZE;
      }
    }

    Map<String, Integer> result = new HashMap<>();
    System.out.println("111111");
    for (CompletableFuture<Map<String, Integer>> future : futures) {
      future.whenComplete(
          (v, e) -> {
            String tempKey;
            System.out.println("222222");
            for (Map.Entry<String, Integer> entry : v.entrySet()) {
              tempKey = entry.getKey();
              if (result.containsKey(tempKey)) {
                result.put(tempKey, result.get(tempKey) + entry.getValue());
              } else {
                result.put(tempKey, entry.getValue());
              }
            }
          });
    }
    System.out.println("333333");

    result.entrySet().stream()
        .sorted((item1, item2) -> item2.getValue() - item1.getValue())
        .limit(3)
        .forEach(
            item -> {
              System.out.println(item.getKey());
              System.out.println(item.getValue());
            });

    THREAD_POOL.shutdown();
  }
}
