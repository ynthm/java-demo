package com.ynthm.algorithm.sort;

/**
 * 计数排序
 *
 * @see <a href="https://en.wikipedia.org/wiki/Counting_sort">Counting Sort (Wikipedia)</a> * <br>
 * @author Ynthm Wang <ynthm.w@gmail.com>
 */
public class CountingSort {
  private CountingSort() {}

  public static void sort(int[] unsorted) {
    int maxValue = findMax(unsorted);
    int[] counts = new int[maxValue + 1]; // counts number of elements
    updateCounts(unsorted, counts);
    populateCounts(unsorted, counts);
  }
  // finding maximum value in unsorted array
  private static int findMax(int[] unsorted) {
    int max = Integer.MIN_VALUE; // assume minimum value(-2147483648) of interger is maximum
    for (int i : unsorted) {
      if (i > max) max = i;
    }
    return max;
  }
  // Incrementing the number of counts in unsorted array
  private static void updateCounts(int[] unsorted, int[] counts) {
    for (int e : unsorted) counts[e]++;
  }

  private static void populateCounts(int[] unsorted, int[] counts) {
    int index = 0;
    for (int i = 0; i < counts.length; i++) {
      int e = counts[i];
      while (e > 0) {
        unsorted[index++] = i;
        e--;
      }
    }
  }
}
