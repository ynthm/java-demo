package com.ynthm.demo.jdk8.test;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class BitMap {

  /** 2^5 = 32 */
  private static final int ADDRESS_BITS_PER_WORD = 5;

  private int[] words;

  public BitMap(long size) {
    // int arrSize = (int) Math.ceil(size / 32);
    this.words = new int[(int) size >> ADDRESS_BITS_PER_WORD + ((size & 31) > 0 ? 1 : 0)];
  }

  public void set(int bitIndex) {
    if (bitIndex < 0) {
      throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
    }
    // 求出 bitIndex 数组下标 相当于 bitIndex / 32
    int wordIndex = wordIndex(bitIndex);
    // Restores invariants
    words[wordIndex] |= (1 << bitIndex);
  }

  public void clear(int bitIndex) {
    if (bitIndex < 0) {
      throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
    }

    int wordIndex = wordIndex(bitIndex);
    words[wordIndex] &= ~(1 << bitIndex);
  }

  public boolean get(int bitIndex) {
    if (bitIndex < 0) {
      throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
    }

    int wordIndex = wordIndex(bitIndex);
    return (words[wordIndex] & (1 << bitIndex)) != 0;
  }

  private static int wordIndex(int bitIndex) {
    return bitIndex >> ADDRESS_BITS_PER_WORD;
  }
}
