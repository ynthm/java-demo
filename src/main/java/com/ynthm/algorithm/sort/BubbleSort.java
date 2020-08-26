package com.ynthm.algorithm.sort;

/**
 * 冒泡排序
 *
 * @author ethan
 */
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

  /**
   * 设置一个标志，如果这一趟发生了交换，则为true，否则为false。明显如果有一趟没有发生交换，说明排序已经完成。
   *
   * @param arr
   */
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

  /**
   * 如果有100个数的数组，仅前面10个无序，后面90个都已排好序且都大于前面10个数字， 那么在第一趟遍历后，最后发生交换的位置必定小于10，且这个位置之后的数据必定已经有序了，
   * 记录下这位置，第二次只要从数组头部遍历到这个位置就可以了
   *
   * @param a
   */
  public static void sort2(int[] a) {
    int j, k;
    int flag;

    flag = a.length;
    while (flag > 0) {
      k = flag;
      flag = 0;
      for (j = 1; j < k; j++) {
        if (a[j - 1] > a[j]) {
          SortHelper.swap(a, j - 1, j);
          flag = j;
        }
      }
    }
  }
}
