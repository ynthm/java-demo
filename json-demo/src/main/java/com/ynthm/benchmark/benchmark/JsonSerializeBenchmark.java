package com.ynthm.benchmark.benchmark;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.ynthm.benchmark.entity.Address;
import com.ynthm.benchmark.entity.Hobby;
import com.ynthm.benchmark.entity.User;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/** @author ethan */
@Warmup(iterations = 3, time = 3)
@Measurement(iterations = 10, time = 10)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
public class JsonSerializeBenchmark {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final Gson GSON = new Gson();

  @Benchmark
  public String benchmarkSerializationWithJackson(
      SerializationDataProvider serializationDataProvider) throws IOException {
    return OBJECT_MAPPER.writeValueAsString(serializationDataProvider.object);
  }

  @Benchmark
  public String benchmarkSerializationWithF(SerializationDataProvider serializationDataProvider) {
    return JSON.toJSONString(serializationDataProvider.object);
  }

  @Benchmark
  public String benchmarkSerializationWithG(SerializationDataProvider serializationDataProvider) {
    return GSON.toJson(serializationDataProvider.object);
  }

  @State(Scope.Benchmark)
  public static class SerializationDataProvider {

    private User object;

    @Setup(Level.Invocation)
    public void setup() {
      this.object =
          new User()
              .setFirstName("Ethan")
              .setLastName("Wang")
              .setAddress(
                  new Address()
                      .setCity("深圳")
                      .setCountry("China")
                      .setPostalCode(360000)
                      .setStreet("南山街道")
                      .setStreetNumber("1111"))
              .setHobbies(
                  Lists.newArrayList(
                      new Hobby()
                          .setName("Soccer")
                          .setTags(
                              Lists.newArrayList(
                                  "Team sport", "Ball", "Outdoor", "Championship"))));
    }
  }
}
