package com.ynthm.pattern.strategy;

public class Subtraction implements Operation {
  @Override
  public int apply(int a, int b) {
    return a - b;
  }
}
