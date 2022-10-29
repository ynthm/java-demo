package com.ynthm.demo.jdk8.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
  private int id;
  private String name;
}
