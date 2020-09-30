package com.ynthm.algorithm.string.matching;

/** Brute-Force是一个纯暴力算法。 */
public class BF {
  void bruteForce(String s, String p) {
    int sLen = s.length();
    int pLen = p.length();

    for (int i = 0; i < sLen - pLen; i++) {
      boolean flag = true;
      for (int j = 0; j < pLen; j++) {
        if (s.indexOf(i + j) != p.indexOf(j)) {
          flag = false;
          break;
        }
      }

      if (flag) {
        System.out.println(i);
      }
    }
  }

  public static void main(String[] args) {
    String s = "BBC ABCDABD ABCDABCDABDE";
    String p = "ABCDABD";
    BF bf = new BF();
    bf.bruteForce(s, p);
  }
}
