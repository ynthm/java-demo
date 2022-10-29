package com.ynthm.demo.jdk8;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class FloatTest {

  @Test
  void test() {

    BigDecimal a = new BigDecimal("2.00");
    BigDecimal b = new BigDecimal("2.0");

    System.out.println(a.equals(b)); // false

    System.out.println(a.compareTo(b) == 0); // true
  }

  /** Compare double – Threshold based comparison */
  @Test
  void thresholdBased() {
    final double THRESHOLD = .0001;

    // Method 1
    double f1 = 0.0;
    for (int i = 1; i <= 11; i++) {
      f1 += 0.1;
    }
    // Method 2
    double f2 = 0.1 * 11;

    System.out.println("f1 = " + f1);
    System.out.println("f2 = " + f2);
    // 使用阈值比较2个double
    if (Math.abs(f1 - f2) < THRESHOLD) System.out.println("f1 and f2 are equal using threshold\n");
    else System.out.println("f1 and f2 are not equal using threshold\n");

    BigDecimal a = new BigDecimal(2.9d);
    BigDecimal b = new BigDecimal(7.4d);
    BigDecimal c = new BigDecimal(10.2d);
    System.out.println((a.add(b)).compareTo(c));
  }

  /** Compare double – Compare with BigDecimal */
  @Test
  void compareWithBigDecimal() {
    // Method 1
    BigDecimal f1 = new BigDecimal("0.0");
    BigDecimal pointOne = new BigDecimal("0.1");
    for (int i = 1; i <= 11; i++) {
      f1 = f1.add(pointOne);
    }

    // Method 2
    BigDecimal f2 = new BigDecimal("0.1");
    BigDecimal eleven = new BigDecimal("11");
    f2 = f2.multiply(eleven);

    System.out.println("f1 = " + f1);
    System.out.println("f2 = " + f2);

    if (f1.compareTo(f2) == 0) System.out.println("f1 and f2 are equal using BigDecimal\n");
    else System.out.println("f1 and f2 are not equal using BigDecimal\n");
  }
}
