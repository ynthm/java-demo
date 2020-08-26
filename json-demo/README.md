```sh
mvn clean install  -DskipTests
java -jar target/benchmarks.jar StringConnectBenchmark
```



```haskell
Benchmark                                Mode  Cnt   Score    Error  Units
JMHSample_16_CompilerControl.baseline    avgt    3   0.300 ±  0.090  ns/op
JMHSample_16_CompilerControl.blank       avgt    3   0.323 ±  0.544  ns/op
JMHSample_16_CompilerControl.dontinline  avgt    3   2.099 ±  7.515  ns/op
JMHSample_16_CompilerControl.exclude     avgt    3  56.809 ± 20.612  ns/op
JMHSample_16_CompilerControl.inline      avgt    3   0.308 ±  0.264  ns/op
```



#### 使用 fork 避免 JIT 优化干扰

```haskell
Benchmark                                 Mode  Cnt   Score   Error  Units
JMHSample_12_Forking.measure_1_c1         avgt    5   2.518 ± 0.622  ns/op
JMHSample_12_Forking.measure_2_c2         avgt    5  14.080 ± 0.283  ns/op
JMHSample_12_Forking.measure_3_c1_again   avgt    5  13.462 ± 0.164  ns/op
JMHSample_12_Forking.measure_4_forked_c1  avgt    5   3.861 ± 0.712  ns/op
JMHSample_12_Forking.measure_5_forked_c2  avgt    5   3.574 ± 0.220  ns/op
```



#### 不要使用循环来测试

```haskell
Benchmark                               Mode  Cnt  Score   Error  Units
JMHSample_11_Loops.measureRight         avgt    5  2.343 ± 0.199  ns/op
JMHSample_11_Loops.measureWrong_1       avgt    5  2.358 ± 0.166  ns/op
JMHSample_11_Loops.measureWrong_10      avgt    5  0.326 ± 0.354  ns/op
JMHSample_11_Loops.measureWrong_100     avgt    5  0.032 ± 0.011  ns/op
JMHSample_11_Loops.measureWrong_1000    avgt    5  0.025 ± 0.002  ns/op
JMHSample_11_Loops.measureWrong_10000   avgt    5  0.022 ± 0.005  ns/op
JMHSample_11_Loops.measureWrong_100000  avgt    5  0.019 ± 0.001  ns/op
```



JMH plugin for IDEA
装完插件重启IDEA 就可以右键运行单个测试方法

