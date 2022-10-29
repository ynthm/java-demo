package com.ynthm.json.json.gson;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author ethan
 */
@Data
public class User {
  private String name;
  private int age;

  /** json字符串字段为email或e-mail 或 email_address emailAddress或 时都可正确解析 */
  @SerializedName(
      value = "email",
      alternate = {"e-mail", "email_address"})
  private String emailAddress;
}
