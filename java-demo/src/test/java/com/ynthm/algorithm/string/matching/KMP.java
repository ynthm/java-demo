package com.ynthm.algorithm.string.matching;

import java.util.ArrayList;
import java.util.List;

/** KMP 算法（Knuth-Morris-Pratt 算法）是一个著名的字符串匹配算法 */
public class KMP {

  /** 在文本 text 中寻找模式串 pattern，返回所有匹配的位置开头 */
  public List<Integer> search(String text, String pattern) {
    List<Integer> positions = new ArrayList<>();
    int[] maxMatchLengths = calculateMaxMatchLengths(pattern);
    int count = 0;
    for (int i = 0; i < text.length(); i++) {
      while (count > 0 && pattern.charAt(count) != text.charAt(i)) {
        count = maxMatchLengths[count - 1];
      }
      if (pattern.charAt(count) == text.charAt(i)) {
        count++;
      }
      if (count == pattern.length()) {
        positions.add(i - pattern.length() + 1);
        count = maxMatchLengths[count - 1];
      }
    }
    return positions;
  }

  /** 构造模式串 pattern 的最大匹配数表 Partial Match Table */
  private int[] calculateMaxMatchLengths(String pattern) {
    int[] maxMatchLengths = new int[pattern.length()];
    int maxLength = 0;
    for (int i = 1; i < pattern.length(); i++) {
      while (maxLength > 0 && pattern.charAt(maxLength) != pattern.charAt(i)) {
        maxLength = maxMatchLengths[maxLength - 1];
      }
      if (pattern.charAt(maxLength) == pattern.charAt(i)) {
        maxLength++;
      }
      maxMatchLengths[i] = maxLength;
    }
    return maxMatchLengths;
  }

  public static void main(String[] args) {
    String s = "BBC ABCDABD ABCDABCDABDE";
    String p = "ABCDABD";
    KMP kmp = new KMP();
    System.out.println(kmp.search(s, p));
  }
}
