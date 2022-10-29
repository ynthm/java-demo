package com.ynthm.demo.jdk8;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ynthm
 * @version 1.0
 */
public class ArrayDemo {

  public static void main(String[] args) {
    // 慎用 Arrays.asList
    int[] a = {1, 3, 6, 2, 7, 9};
    // 二叉查找
    System.out.println(Arrays.binarySearch(a, 6));
    // 双轴快速排序
    Arrays.sort(a);
    System.out.println("排序后的数组为：");
    System.out.println(Arrays.toString(a));

    int[] b = new int[8];
    // 所有元素填充-1
    Arrays.fill(b, -1);
    System.out.println(Arrays.toString(b));
    // System.arraycopy
    int[] ints = Arrays.copyOf(a, 10);
    System.out.println(Arrays.toString(ints));

    Map<Integer, String> map = new LinkedHashMap<>(3, 0.75f, true);
    map.put(1, "001");
    map.put(2, "002");

    map.get(1);
    map.get(2);
    map.get(1);
  }
}
