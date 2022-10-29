package com.ynthm.json.benchmark.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ethan
 */
@Accessors(chain = true)
@Data
public class Address {

  private String street;
  private String streetNumber;
  private String city;
  private String country;
  private Integer postalCode;
}
