package com.ynthm.json.benchmark.runner;

import com.ynthm.json.benchmark.benchmark.JsonDeserializeBenchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author ethan
 */
public class JsonBenchmarkRunner {
  public static void main(String[] args) throws RunnerException {
    Options opt =
        new OptionsBuilder()
            // 导入要测试的类
            .include(JsonDeserializeBenchmark.class.getSimpleName())
            .build();

    new Runner(opt).run();
  }
}
