package com.ynthm.jdk;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.RadixUtil;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class JdkTest {

  @Test
  void test() {

    System.out.println(Integer.toBinaryString(1024));
    System.out.println(Integer.toString(14, 7));
    System.out.println(Integer.valueOf("FFFF", 16));
    System.out.println(Integer.parseInt("1111", 2));
    // 二进制1的个数
    System.out.println(Integer.bitCount(21));
    System.out.println(Instant.ofEpochSecond(1627009772));
    long now = System.currentTimeMillis();
    System.out.println(now);
    System.out.println(Long.toHexString(now));
    int now1 = (int) now;
    System.out.println(now1);
    String s = Integer.toHexString(now1);
    System.out.println(s);
    System.out.println(Integer.parseUnsignedInt(s, 16));
    System.out.println(Long.parseLong(s, 16));
    System.out.println(Long.parseLong(Long.toHexString(now), 16));
    System.out.println(HexUtil.toBigInteger(Long.toHexString(now)));
  }

  @Test
  void test4() {
    System.out.println(TimeUnit.DAYS.toSeconds(1));
  }

  @Test
  void test5() {
    System.out.println(RadixUtil.encode(RadixUtil.RADIXS_34, 100));
    List<Integer> integers = ListUtil.toList(1, 2, 3);
    Collections.shuffle(integers);
    System.out.println(integers);
  }

  public static void shuffle(String str, Random rnd) {
    int size = str.length();
    char[] arr = str.toCharArray();
    // Shuffle array
    for (int i = size; i > 1; i--) swap(arr, i - 1, rnd.nextInt(i));
  }

  private static void swap(char[] arr, int i, int j) {
    char tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }
}
