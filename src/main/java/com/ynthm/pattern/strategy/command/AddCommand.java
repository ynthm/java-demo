package com.ynthm.pattern.strategy.command;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddCommand implements Command {
  private int a;
  private int b;

  @Override
  public Integer execute() {
    return a + b;
  }
}
