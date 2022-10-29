package com.ynthm.json.benchmark.runner;

import com.ynthm.json.benchmark.benchmark.StringConnectBenchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * @author ethan
 */
public class StringBuilderRunner {
  public static void main(String[] args) throws RunnerException {
    Options opt =
        new OptionsBuilder()
            // 导入要测试的类
            .include(StringConnectBenchmark.class.getSimpleName())
            // 预热5轮
            .warmupIterations(3)
            .warmupTime(TimeValue.seconds(2))
            // 度量10轮
            .measurementIterations(5)
            .measurementTime(TimeValue.seconds(3))
            .mode(Mode.Throughput)
            .forks(2)
            .result("result.json")
            .resultFormat(ResultFormatType.JSON)
            .build();

    new Runner(opt).run();
  }
}
