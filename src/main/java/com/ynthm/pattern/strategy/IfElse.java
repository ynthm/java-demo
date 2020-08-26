package com.ynthm.pattern.strategy;

public class IfElse {
  public int calcutlate(int a, int b, String operator) {
    int result = Integer.MIN_VALUE;
    if ("add".equals(operator)) {
      result = a + b;
    } else if ("subtract".equals(operator)) {
      result = a - b;
    } else if ("multiply".equals(operator)) {
      result = a * b;
    } else if ("divide".equals(operator)) {
      result = a / b;
    }
    return result;
  }

  public int calcutlateUsingSwitch(int a, int b, String operator) {
    int result;
    switch (operator) {
      case "add":
        result = a + b;
        break;
      case "subtract":
        result = a - b;
        break;
      case "multiply":
        result = a * b;
        break;
      case "divide":
        result = a * b;
        break;
      default:
        result = Integer.MIN_VALUE;
    }
    return result;
  }
}
