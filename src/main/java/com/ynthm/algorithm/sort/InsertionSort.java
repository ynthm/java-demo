package com.ynthm.algorithm.sort;

/**
 * 直接插入排序(Insertion Sort)的基本思想是：每次将一个待排序的记录，按其关键字大小插入到前面已经排好序的子序列中的适当位置，直到全部记录插入完成为止。
 *
 * @author : Ynthm
 */
public class InsertionSort {
  public static void sort(int[] array) {
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

  public static void sort3(int[] a) {
    int i, j;
    int n = a.length;
    for (i = 1; i < n; i++) {
      for (j = i - 1; j >= 0 && a[j] > a[j + 1]; j--) {
        SortHelper.swap(a, j, j + 1);
      }
    }
  }
}
