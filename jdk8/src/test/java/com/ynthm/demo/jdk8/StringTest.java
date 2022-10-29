package com.ynthm.demo.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class StringTest {

  @Test
  void join() {
    String[] strings = {"apple", "orange"};
    System.out.println(String.join(",", strings));

    List<String> strList = Arrays.asList("I", "Love", "Make");
    System.out.println(String.join("--", strList));

    StringJoiner joiner = new StringJoiner(", ", "[", "]");

    joiner.add("How").add("Are").add("You");
    System.out.println(joiner);

    List<String> numbers = Arrays.asList("5", "2", "0");

    String joinedString = numbers.stream().collect(Collectors.joining("\uD83D\uDC8B", "[", "]"));

    System.out.println(joinedString);
  }
}
