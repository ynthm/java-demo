package com.ynthm.pattern.strategy.rule;

public interface Rule {
  boolean evaluate(Expression expression);

  int getResult();
}
