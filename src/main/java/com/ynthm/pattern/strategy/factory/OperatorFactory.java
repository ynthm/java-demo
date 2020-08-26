package com.ynthm.pattern.strategy.factory;

import com.ynthm.pattern.strategy.Additon;
import com.ynthm.pattern.strategy.Operation;
import com.ynthm.pattern.strategy.Subtraction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OperatorFactory {
  private static Map<String, Operation> operationMap = new HashMap<>();

  static {
    operationMap.put("add", new Additon());
    operationMap.put("subtract", new Subtraction());
  }

  public static Optional<Operation> getOperation(String operatiion) {
    return Optional.ofNullable(operationMap.get(operatiion));
  }

  /**
   * 使用示例
   *
   * @param a
   * @param b
   * @param operator
   * @return
   */
  public int calculateUsingFactory(int a, int b, String operator) {
    Operation targetOperation =
        OperatorFactory.getOperation(operator)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Operator"));
    return targetOperation.apply(a, b);
  }
}
