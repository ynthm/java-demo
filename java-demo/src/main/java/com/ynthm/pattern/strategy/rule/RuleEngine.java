package com.ynthm.pattern.strategy.rule;

import java.util.ArrayList;
import java.util.List;

public class RuleEngine {
  private static List<Rule> ruleList = new ArrayList<>();

  static {
    ruleList.add(new AddRule());
  }

  public int process(Expression expression) {
    Rule rule =
        ruleList.stream()
            .filter(r -> r.evaluate(expression))
            .findFirst()
            .orElseThrow(
                () -> new IllegalArgumentException("Expression does not matches any rule"));
    return rule.getResult();
  }
}
