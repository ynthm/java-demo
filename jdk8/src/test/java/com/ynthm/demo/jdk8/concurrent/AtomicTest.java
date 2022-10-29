package com.ynthm.demo.jdk8.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {

  /** AtomicInteger as atomic counter */
  @Test
  void integer() {

    // Initial value is 0
    AtomicInteger atomicInteger0 = new AtomicInteger();
    System.out.println(atomicInteger0);
    atomicInteger0.set(123);
    System.out.println(atomicInteger0);

    AtomicInteger atomicInteger = new AtomicInteger(100);
    System.out.println(atomicInteger.get());

    System.out.println(atomicInteger.addAndGet(2)); // 102
    System.out.println(atomicInteger.toString()); // 102

    System.out.println(atomicInteger.getAndAdd(2)); // 102
    System.out.println(atomicInteger); // 104

    // ++i
    System.out.println(atomicInteger.incrementAndGet()); // 105
    System.out.println(atomicInteger); // 105
    // i++
    System.out.println(atomicInteger.getAndIncrement()); // 105
    System.out.println(atomicInteger); // 106
    // --i
    System.out.println(atomicInteger.decrementAndGet()); // 105
    System.out.println(atomicInteger); // 105
    // i--
    System.out.println(atomicInteger.getAndDecrement()); // 105
    System.out.println(atomicInteger); // 104
  }

  @Test
  void integerCas() {
    AtomicInteger atomicInteger = new AtomicInteger(100);

    boolean isSuccess = atomicInteger.compareAndSet(100, 110); // current value 100

    System.out.println(isSuccess); // true
    System.out.println(atomicInteger);

    isSuccess = atomicInteger.compareAndSet(100, 120); // current value 110

    System.out.println(isSuccess); // false
    System.out.println(atomicInteger);
  }
}
