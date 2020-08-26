package com.ynthm.algorithm.sort;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SearchTest {

  @Test
  void search() {
    int[] sortedArr = {3, 4, 5, 6, 7, 8, 9};
    int i = Arrays.binarySearch(sortedArr, 8);
    Assertions.assertEquals(sortedArr[i], 8);
    Assertions.assertEquals(i, 5);
  }
}
