package com.ynthm.algorithm.sort;

/** Author : Ynthm */
public class QuickSort {
  /*
   * 快速排序
   * 两个方向，左边的i下标一直往右走，当a[i] <= a[center_index]，
   * 其中center_index是中枢元素的数组下标，而右边的j下标一直往左走，当a[j] > a[center_index]
   * 如果i和j都走不动了，i <= j, 交换a[i]和a[j],重复上面的过程，直到i>j
   * 交换a[j]和a[center_index]，完成一趟快速排序
   * 枢轴采用三数中值分割法可以优化
   */
  // 递归快速排序
  public static void quickSort(int a[]) {
    qSort(a, 0, a.length - 1);
  }
  // 递归排序，利用两路划分
  public static void qSort(int a[], int low, int high) {
    int pivot = 0;
    if (low < high) {
      // 将数组一分为二
      pivot = partition(a, low, high);
      // 对第一部分进行递归排序
      qSort(a, low, pivot);
      // 对第二部分进行递归排序
      qSort(a, pivot + 1, high);
    }
  }
  // partition函数，实现三数中值分割法
  public static int partition(int a[], int low, int high) {
    int pivotkey = a[low]; // 选取第一个元素为枢轴记录
    while (low < high) {
      // 将比枢轴记录小的交换到低端
      while (low < high && a[high] >= pivotkey) {
        high--;
      }
      // 采用替换而不是交换的方式操作
      a[low] = a[high];
      // 将比枢轴记录大的交换到高端
      while (low < high && a[low] <= pivotkey) {
        low++;
      }
      a[high] = a[low];
    }
    // 枢纽所在位置赋值
    a[low] = pivotkey;
    // 返回枢纽所在的位置
    return low;
  }
}
