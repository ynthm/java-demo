package com.ynthm.algorithm.sort;

/** 插入排序 Author : Ynthm */
public class InserionSort {
  public static void insertionSort(int[] array) {
    int tmp;
    for (int i = 1; i < array.length; i++) {
      tmp = array[i]; // 将当前位置的数给tmp
      int j = i;
      for (; j > 0 && array[j - 1] > tmp; j--) {
        /*
         * 往右移，腾出左边的位置,
         * array[j-1]>tmp:大于号是升序排列，小于号是降序排列
         */
        array[j] = array[j - 1];
      }
      // 将当前位置的数插入到合适的位置
      array[j] = tmp;
    }
  }
}
