package com.ynthm.json.json.fastjson;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * @author ethan
 */
@Data
@Accessors(chain = true)
public class Person {

  @JSONField(name = "AGE", serialize = false)
  private int age;

  @JSONField(name = "LAST NAME", ordinal = 2)
  private String lastName;

  @JSONField(name = "FIRST NAME", ordinal = 1)
  private String firstName;

  @JSONField(name = "DATE OF BIRTH", format = "dd/MM/yyyy", ordinal = 3)
  private LocalDate birthDay;
}
