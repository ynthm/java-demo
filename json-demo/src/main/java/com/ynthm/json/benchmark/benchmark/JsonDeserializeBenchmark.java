package com.ynthm.json.benchmark.benchmark;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ynthm.json.benchmark.entity.User;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author ethan
 */
@Warmup(iterations = 3, time = 3)
@Measurement(iterations = 10, time = 10)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
public class JsonDeserializeBenchmark {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final Gson GSON = new Gson();

  @Benchmark
  public User benchmarkSerializationWithJackson(SerializationDataProvider serializationDataProvider)
      throws IOException {
    return OBJECT_MAPPER.readValue(serializationDataProvider.jsonString, User.class);
  }

  @Benchmark
  public User benchmarkSerializationWithF(SerializationDataProvider serializationDataProvider) {
    return JSON.parseObject(serializationDataProvider.jsonString, User.class);
  }

  @Benchmark
  public User benchmarkSerializationWithG(SerializationDataProvider serializationDataProvider) {
    return GSON.fromJson(serializationDataProvider.jsonString, User.class);
  }

  @State(Scope.Benchmark)
  public static class SerializationDataProvider {

    private String jsonString;

    @Setup(Level.Invocation)
    public void setup() {
      this.jsonString =
          "{\"firstName\": \"Mike\", \"lastName\":\"Duke\", \"hobbies\": [{\"name\": \"Soccer\", "
              + "\"tags\": [\"Team sport\", \"Ball\", \"Outdoor\", \"Championship\"]}], \"address\":"
              + " { \"street\": \"Main street\", \"streetNumber\": \"1A\", \"city\": \"New York\", \"country\":\"USA\", "
              + "\"postalCode\": 1337}}";
    }
  }
}
