package com.ynthm.jdk.serialization;

import lombok.Data;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;

/** @author ethan */
@Data
public class Person implements Serializable {
  private String name;

  public void sayHi(Function<Integer, String> function) {
    //  1      abc
    Integer var1 = 2;
    String abc = function.apply(var1);

    System.out.println(abc);

    // System.out.println("Hi");
  }

  public void sayHello(Consumer<Integer> consumer) {

    //
    consumer.accept(1);

    //

  }
}
