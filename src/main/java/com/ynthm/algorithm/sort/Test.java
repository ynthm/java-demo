package com.ynthm.algorithm.sort;

/** Author : Ynthm */
public class Test {

  public static void main(String[] args) {
    int[] arr = {8, 5, 7, 6, 9, 4, 3, 2, 1};
    BubbleSort.sort(arr);
    for (int i : arr) {
      System.out.println(i);
    }
  }
}
