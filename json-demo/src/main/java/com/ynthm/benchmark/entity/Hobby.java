package com.ynthm.benchmark.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/** @author ethan */
@Accessors(chain = true)
@Data
public class Hobby {

  private String name;
  private List<String> tags;
}
