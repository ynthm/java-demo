package com.ynthm.pattern.strategy.command;

public class CommandCalculator {
  public int calculate(Command command) {
    return command.execute();
  }
}
