package com.ynthm.jdk.serialization;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Ynthm
 * @version 1.0
 */
public class PersonB {

  public static void main(String[] args) {
    Person person = new Person();

    person.sayHi(
        a -> {
          String s = a + " oh";
          return s;
        });

    Collections.sort(new ArrayList<Integer>(), Integer::compareTo);

    person.sayHello(
        a -> {
          System.out.println(a);
        });
  }
}
