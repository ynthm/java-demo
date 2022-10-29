package com.ynthm.demo.jdk8.test;

/**
 * 可以表示数最大范围 4,294,967,296 如果大于这个可以用 long 做 key
 *
 * @author Ynthm Wang
 * @version 1.0
 */
public class TwoBitMap {
  /** 一个32位的int表示多少个key */
  private final int intSize;

  private int[] arr;

  public TwoBitMap(long numSize) {
    this(2, numSize);
  }

  /**
   * @param nBits n个bits 表示一个key 的 2^n个状态 2的整数倍
   * @param numSize 整数范围
   */
  public TwoBitMap(int nBits, long numSize) {
    intSize = 32 / nBits;
    int arrSize = (int) Math.ceil(numSize / intSize);
    arr = new int[arrSize];
  }

  public void set(int key, int status) {
    //  int wordIndex = key / intSize
    int wordIndex = key >> 4;
    // 获得对应的位置
    int n = key % intSize;
    // 对应 key 位置清零 arr[wordIndex] &
    arr[wordIndex] &= ~(0x3 << (2 * n));
    // 重新赋值
    arr[wordIndex] |= (status & 3) << (2 * n);
  }

  /**
   * 如果 key 的数大于3跳过
   *
   * @param key
   */
  public void add(int key) {
    int num = get(key);
    if (key < 3) {
      set(key, num + 1);
    }
  }

  public int get(int key) {
    int m = key >> 4;
    int n = key % intSize;
    return (arr[m] & (0x3 << (2 * n))) >> (2 * n);
  }

  public static void printInfo(int value) {
    System.out.println(value + " 二进制: " + Integer.toBinaryString(value));
  }
}
