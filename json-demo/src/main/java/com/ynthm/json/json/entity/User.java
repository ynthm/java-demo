package com.ynthm.json.json.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author : Ethan Wang
 */
@Data
public class User {
  private String name;
  // 不JSON序列化年龄属性
  @JsonIgnore private Integer age;
  // 格式化日期属性
  @JsonFormat(pattern = "yyyy-MM-DD")
  private LocalDate birthday;
  // 序列化email属性为mail
  @JsonProperty("my_email")
  private String email;
}
