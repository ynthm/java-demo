package com.ynthm.pattern.strategy;

import com.ynthm.pattern.strategy.enums.Calculator;
import com.ynthm.pattern.strategy.enums.Operator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {
  @Test
  void wheCalculateUsingEnumOperator() {
    Calculator calculator = new Calculator();
    int result = calculator.calculate(3, 4, Operator.ADD);
    assertEquals(7, result);
  }
}
