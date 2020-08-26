package com.ynthm.benchmark.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 开启 4 个并发线程 fork 1 个线程
 *
 * @author ethan
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 5, time = 5)
@Fork(1)
@State(Scope.Benchmark)
@Threads(4)
public class AtomicTest {

  public static void main(String[] args) throws RunnerException {
    // 启动基准测试
    Options opt = new OptionsBuilder().include(AtomicTest.class.getSimpleName()).build();
    // 执行测试
    new Runner(opt).run();
  }

  @Benchmark
  public int atomicTest(Blackhole blackhole) {
    AtomicLong atomicInteger = new AtomicLong(0L);
    for (int i = 0; i < 1024; i++) {
      atomicInteger.addAndGet(1L);
    }
    // 为了避免 JIT 忽略未被使用的结果
    return atomicInteger.intValue();
  }

  @Benchmark
  public int longAdderTest(Blackhole blackhole) {
    LongAdder longAdder = new LongAdder();
    for (int i = 0; i < 1024; i++) {
      longAdder.add(1);
    }
    return longAdder.intValue();
  }
}
