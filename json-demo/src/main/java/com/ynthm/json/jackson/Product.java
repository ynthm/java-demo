package com.ynthm.json.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** @author ethan */
@EqualsAndHashCode
@ToString
public class Product {

  @Getter(onMethod_ = {@JsonProperty("fullName")})
  @Setter(onMethod_ = {@JsonProperty("full_name")})
  private String fullName;
}
