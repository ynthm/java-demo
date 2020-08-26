package com.ynthm.jdk.serialization;

import lombok.Data;

import java.io.Serializable;

/** @author ethan */
@Data
public class Person implements Serializable {
  private String name;
}
