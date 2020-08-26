package com.ynthm.tools.util;

import java.math.BigDecimal;

public class RadomUtil {

  /**
   * 获取金额
   *
   * @param min
   * @param max
   * @return
   */
  public static BigDecimal getFloatingPriceBetweenMinAndMax(BigDecimal min, BigDecimal max) {
    float minF = min.floatValue();
    float maxF = max.floatValue();

    // 生成随机数
    BigDecimal db = new BigDecimal(Math.random() * (maxF - minF) + minF);
    int scale = min.scale() > max.scale() ? min.scale() : max.scale();

    // 返回保留两位小数的随机数。不进行四舍五入
    return db.setScale(scale, BigDecimal.ROUND_DOWN);
  }

  /**
   * @param basic 基准
   * @param floatingRange 浮动范围
   * @return
   */
  public static BigDecimal getFloatingPrice(BigDecimal basic, BigDecimal floatingRange) {
    float minF = basic.floatValue();

    // 生成随机数
    BigDecimal db = new BigDecimal(Math.random() * floatingRange.floatValue() + minF);

    // 返回保留两位小数的随机数。不进行四舍五入
    return db.setScale(basic.scale(), BigDecimal.ROUND_DOWN);
  }

  public static void main(String[] args) {
    BigDecimal min = new BigDecimal("3.000");
    BigDecimal floatingRange = new BigDecimal("-0.010");
    BigDecimal max = new BigDecimal("3.10");

    for (int i = 0; i < 10; i++) {
      System.out.println(getFloatingPriceBetweenMinAndMax(min, max));
    }
    for (int i = 0; i < 10; i++) {
      System.out.println(getFloatingPrice(min, floatingRange));
    }
  }
}
