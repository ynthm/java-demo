package com.ynthm.algorithm.sort;

/** 希尔排序 Author : Ynthm */
public class ShellSort {
  public static void sort(int[] array) {
    int j;
    for (int gap = array.length / 2; gap > 0; gap /= 2) {
      // 定义一个增长序列，即分割数组的增量,d1=N/2   dk=(d(k-1))/2
      for (int i = gap; i < array.length; i++) {
        int tmp = array[i];
        for (j = i; j >= gap && tmp < array[j - gap]; j -= gap) {
          // 将相距为Dk的元素进行排序
          array[j] = array[j - gap];
        }
        array[j] = tmp;
      }
    }
  }

  public static void sort3(int[] a) {
    int i, j, gap;
    int n = a.length;
    for (gap = n / 2; gap > 0; gap /= 2) {
      for (i = gap; i < n; i++) {
        for (j = i - gap; j >= 0 && a[j] > a[j + gap]; j -= gap) {
          SortHelper.swap(a, j, j + gap);
        }
      }
    }
  }
}
