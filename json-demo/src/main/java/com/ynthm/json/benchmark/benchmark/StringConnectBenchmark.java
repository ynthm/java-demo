package com.ynthm.json.benchmark.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * @author ethan
 */
@State(value = Scope.Benchmark)
@Threads(4)
public class StringConnectBenchmark {

  @Param(value = {"10", "100", "1000"})
  private int length;

  /** 字符串拼接之 StringBuilder 基准测试 */
  @Benchmark
  public void testStringBuilder(Blackhole blackhole) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append("a");
    }
    blackhole.consume(sb.toString());
  }

  /** 字符串拼接之直接相加基准测试 */
  @Benchmark
  public void testStringAdd(Blackhole blackhole) {
    String a = "";
    for (int i = 0; i < length; i++) {
      a += "a";
    }
    blackhole.consume(a);
  }

  /** 字符串拼接之 StringBuffer 基准测试 */
  @Benchmark
  public void testStringBuffer(Blackhole blackhole) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
      sb.append(i);
    }
    blackhole.consume(sb.toString());
  }

  /** 字符串拼接之String Concat基准测试 */
  @Benchmark
  public void testStringConcat(Blackhole blackhole) {
    String a = "";
    for (int i = 0; i < length; i++) {
      a.concat("a");
    }
    blackhole.consume(a);
  }

  /** 字符串拼接之 StringFormat 基准测试 */
  //  @Benchmark
  //  public String testStringFormat() {
  //    return String.format("%s%s%s", 1, 2, 3);
  //  }
}
