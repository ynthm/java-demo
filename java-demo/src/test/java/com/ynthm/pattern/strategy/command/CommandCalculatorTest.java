package com.ynthm.pattern.strategy.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandCalculatorTest {
  @Test
  void whenCalculateUsingCommand() {
    CommandCalculator calculator = new CommandCalculator();
    int result = calculator.calculate(new AddCommand(3, 7));
    assertEquals(10, result);
  }
}
