package com.ynthm.demo.jdk8.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class AnalysisTask implements Supplier<Map<String, Integer>> {

  private int index;
  private int size;

  public AnalysisTask(int index, int size) {
    this.index = index;
    this.size = size;
  }

  @Override
  public Map<String, Integer> get() {
    System.out.println("task begin index: " + index);
    // 通过  index  与 size 去数据库查找

    // 解析数据得到结果
    Random random = new Random();
    Map<String, Integer> result = new HashMap<>();
    result.put("A", random.nextInt(5) + 1);
    result.put("B", random.nextInt(5) + 1);
    result.put("C", random.nextInt(5) + 1);
    result.put("D", random.nextInt(5) + 1);
    try {

      TimeUnit.MICROSECONDS.sleep(random.nextInt(1000) + 1);
      System.out.println("task down");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return result;
  }
}
