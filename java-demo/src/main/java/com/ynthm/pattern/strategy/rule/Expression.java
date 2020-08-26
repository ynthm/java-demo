package com.ynthm.pattern.strategy.rule;

import com.ynthm.pattern.strategy.enums.Operator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Expression {
  private int x;
  private int y;
  private Operator operator;
}
