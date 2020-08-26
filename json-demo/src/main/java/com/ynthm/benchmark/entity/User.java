package com.ynthm.benchmark.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class User {

  private String firstName;
  private String lastName;
  private List<Hobby> hobbies;
  private Address address;
}
