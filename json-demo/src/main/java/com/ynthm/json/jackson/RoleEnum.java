package com.ynthm.json.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** @author ethan */
@Getter
@AllArgsConstructor
public enum RoleEnum {
  /** 管理员 */
  ADMIN(1, "ROLE_ADMIN"),
  USER(2, "ROLE_USER"),
  ;

  /** 值 */
  @JsonValue private int value;

  private String name;

  @JsonCreator
  public static RoleEnum getItem(int value) {
    for (RoleEnum item : values()) {
      if (item.getValue() == value) {
        return item;
      }
    }
    return null;
  }
}
