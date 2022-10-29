package com.ynthm.demo.jdk8.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private Integer id;
  private String name;
  private int age;

  private Long balance;

  private Role role;
}
