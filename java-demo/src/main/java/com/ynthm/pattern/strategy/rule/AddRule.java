package com.ynthm.pattern.strategy.rule;

import com.ynthm.pattern.strategy.enums.Operator;

public class AddRule implements Rule {
  private int result;

  @Override
  public boolean evaluate(Expression expression) {
    boolean evalResult = false;
    if (expression.getOperator() == Operator.ADD) {
      this.result = expression.getX() + expression.getY();
      evalResult = true;
    }
    return evalResult;
  }

  @Override
  public int getResult() {
    return result;
  }
}
