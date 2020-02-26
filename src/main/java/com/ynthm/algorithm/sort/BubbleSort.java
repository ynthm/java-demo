package com.ynthm.algorithm.sort;

/** Author : Ynthm */
public class BubbleSort {

  public static void sort(int[] array) {
    if (array == null || array.length == 0) {
      return;
    }

    int length = array.length;
    // 外层：需要length-1次循环比较
    for (int i = 0; i < length - 1; i++) {
      // 内层：每次循环需要两两比较的次数，每次比较后，都会将当前最大的数放到最后位置，所以每次比较次数递减一次
      for (int j = 0; j < length - 1 - i; j++) {
        if (array[j] > array[j + 1]) {
          // 交换数组array的j和j+1位置的数据
          SortHelper.swap(array, j, j + 1);
        }
      }
    }
  }

  public void sort1(int[] arr) {

    for (int i = 1; i < arr.length; i++) {
      // 设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。
      boolean flag = true;

      for (int j = 0; j < arr.length - i; j++) {
        if (arr[j] > arr[j + 1]) {
          SortHelper.swap(arr, j, j + 1);
          flag = false;
        }
      }

      if (flag) {
        break;
      }
    }
  }
}
