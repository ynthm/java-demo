package com.ynthm.demo.jdk8.introspector;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Data
public class User {
  private Long id;
  private String name;
  private Integer age;
  private LocalDate birth;
  private BigDecimal balance;
}
