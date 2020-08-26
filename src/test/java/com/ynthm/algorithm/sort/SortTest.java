package com.ynthm.algorithm.sort;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class SortTest {

  private int[] sortedArr = {3, 4, 5, 6, 7, 8, 9};
  private int[] arr = {8, 5, 7, 6, 9, 4, 3};

  @BeforeEach
  void init() {
    System.out.println("Before sort:" + Arrays.toString(arr));
  }

  @AfterEach
  void tearDown() {
    System.out.println("After  sort:" + Arrays.toString(arr));
  }

  @Test
  void testJdkSort() {
    Arrays.sort(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testBubbleSort() {
    BubbleSort.sort(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testBubbleSort2() {
    BubbleSort.sort2(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testBucketSort() {
    BucketSort.bucketSort(arr, 0, 9 + 1);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testCountingSort() {
    CountingSort.sort(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testHeapSort() {
    HeapSort.sort(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testInsertionSort() {
    InsertionSort.sort(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testMergeSort() {
    MergeSort.sort(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testQuickSort() {
    QuickSort.sort(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testRadixSort() {
    RadixSort.sort(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testRadixSort1() {
    RadixSort.sort1(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testRadixSort2() {
    RadixSort.sort(arr, 1);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testSelectSort() {
    SelectSort.sort(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }

  @Test
  void testShellSort() {
    ShellSort.sort(arr);
    Assertions.assertTrue(Arrays.equals(arr, sortedArr));
  }
}
