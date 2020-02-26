package com.ynthm.algorithm.sort;

import java.util.Arrays;

public class RadixSort {

  private static final int NUMBER_OF_BUCKETS = 10; // 10 for base 10 numbers

  public static void sort(int[] nums) {
    // 找到最大数
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; i++) max = Math.max(max, nums[i]);

    int i, exp = 1;
    int[] tmp = new int[nums.length]; // 用于存储桶排序时重新整理出的nums
    while (max > 0) {
      int[] bucket = new int[10]; // 每次都需要一个新的桶

      for (int num : nums) // 计算每个桶中有几个数字
      bucket[num / exp % 10]++;
      // 统计当前桶之前的数字有几个，
      // 这些数字就是当前桶中数字在数组中新位置的最低下标
      for (i = 1; i < bucket.length; i++) bucket[i] += bucket[i - 1];
      for (i = bucket.length - 1; i > 0; i--) bucket[i] = bucket[i - 1];
      bucket[0] = 0;

      for (i = 0; i < nums.length; i++) tmp[bucket[(nums[i] / exp) % 10]++] = nums[i];

      for (i = 0; i < nums.length; i++) nums[i] = tmp[i];

      max /= 10;
      exp *= 10;
    }
  }

  /**
   * @param arr 待排序数组
   * @param d 表示最大的数有多少位
   */
  public static void sort(int[] arr, int d) {
    int k = 0;
    int n = 1;
    int m = 1; // 控制键值排序依据在哪一位
    int[][] temp = new int[10][arr.length]; // 数组的第一维表示可能的余数0-9
    int[] order = new int[10]; // 数组orderp[i]用来表示该位是i的数的个数
    while (m <= d) {
      for (int i = 0; i < arr.length; i++) {
        int lsd = ((arr[i] / n) % 10);
        temp[lsd][order[lsd]] = arr[i];
        order[lsd]++;
      }
      for (int i = 0; i < 10; i++) {
        if (order[i] != 0)
          for (int j = 0; j < order[i]; j++) {
            arr[k] = temp[i][j];
            k++;
          }
        order[i] = 0;
      }
      n *= 10;
      k = 0;
      m++;
    }
  }

  public static void sort1(int[] unsorted) {
    int[][] buckets = new int[NUMBER_OF_BUCKETS][10];
    for (int i = 0; i < NUMBER_OF_BUCKETS; i++)
      buckets[i][0] = 1; // Size is one since the size is stored in first element
    int numberOfDigits = getMaxNumberOfDigits(unsorted); // Max number of digits
    int divisor = 1;
    for (int n = 0; n < numberOfDigits; n++) {
      int digit = 0;
      for (int d : unsorted) {
        digit = getDigit(d, divisor);
        buckets[digit] = add(d, buckets[digit]);
      }
      int index = 0;
      for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
        int[] bucket = buckets[i];
        int size = bucket[0];
        for (int j = 1; j < size; j++) {
          unsorted[index++] = bucket[j];
        }
        buckets[i][0] = 1; // reset the size
      }
      divisor *= 10;
    }
  }

  private static int getMaxNumberOfDigits(int[] unsorted) {
    int max = Integer.MIN_VALUE;
    int temp = 0;
    for (int i : unsorted) {
      temp = (int) Math.log10(i) + 1;
      if (temp > max) max = temp;
    }
    return max;
  }

  private static int getDigit(int integer, int divisor) {
    return (integer / divisor) % 10;
  }

  private static int[] add(int integer, int[] bucket) {
    int size = bucket[0]; // size is stored in first element
    int length = bucket.length;
    int[] result = bucket;
    if (size >= length) {
      result = Arrays.copyOf(result, ((length * 3) / 2) + 1);
    }
    result[size] = integer;
    result[0] = ++size;
    return result;
  }
}
