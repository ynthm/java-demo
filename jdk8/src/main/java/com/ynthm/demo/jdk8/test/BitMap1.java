package com.ynthm.demo.jdk8.test;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class BitMap1 {

  /** int[] 一个 int 32 个 bits, 需要 5 address bits. 32 = 2^5 = 1<<5 */
  private static final int ADDRESS_BITS_PER_WORD = 5;

  /** 32 */
  private static final int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;

  /** 31 */
  private static final int BIT_INDEX_MASK = BITS_PER_WORD - 1;

  private static final int[] BIT_VALUE = {
    0x00000001,
    0x00000002,
    0x00000004,
    0x00000008,
    0x00000010,
    0x00000020,
    0x00000040,
    0x00000080,
    0x00000100,
    0x00000200,
    0x00000400,
    0x00000800,
    0x00001000,
    0x00002000,
    0x00004000,
    0x00008000,
    0x00010000,
    0x00020000,
    0x00040000,
    0x00080000,
    0x00100000,
    0x00200000,
    0x00400000,
    0x00800000,
    0x01000000,
    0x02000000,
    0x04000000,
    0x08000000,
    0x10000000,
    0x20000000,
    0x40000000,
    0x80000000
  };

  private int[] words;

  public BitMap1(long size) {
    // int arrSize = (int) Math.ceil(size / 32);
    this.words = new int[(int) size >> ADDRESS_BITS_PER_WORD + ((size & 31) > 0 ? 1 : 0)];
  }

  public void set(int bitIndex) {
    if (bitIndex < 0) {
      throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
    }
    // 求出 bitIndex 数组下标 相当于 bitIndex / 32
    int wordIndex = wordIndex(bitIndex);
    // 求出该值的偏移量(求余),等价于"n%31"
    int offset = bitIndex & BIT_INDEX_MASK;

    // Restores invariants
    words[wordIndex] |= BIT_VALUE[offset];
  }

  public boolean get(int bitIndex) {
    if (bitIndex < 0) {
      throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
    }
    int wordIndex = wordIndex(bitIndex);
    int offset = bitIndex & BIT_INDEX_MASK;
    int bits = words[wordIndex];
    // System.out.println("n="+n+",index="+index+",offset="+offset+",bits="+Integer.toBinaryString(bitsMap[index]));
    return ((bits & BIT_VALUE[offset])) != 0;
  }

  private static int wordIndex(int bitIndex) {
    return bitIndex >> ADDRESS_BITS_PER_WORD;
  }

  public static void main(String[] args) {
    int ADDRESS_BITS_PER_WORD = 5;

    int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
    int BIT_INDEX_MASK = BITS_PER_WORD - 1;

    System.out.println(BIT_INDEX_MASK);
  }
}
