package com.ynthm.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** @author ethan */
@Getter
@AllArgsConstructor
public enum ResultCode {
  /** 失败 */
  FAILED(-1, "FAILED"),
  /** 成功 */
  OK(0, "SUCCESS"),
  /** 警告 */
  WARN(1, "WARN"),

  USERNAME_NOT_FOUND(10404, "用户名不存在。"),
  /** 用户输入 */
  VALID_ERROR(20000, "参数校验错误"),

  SERVER_ERROR(50000, "系统错误"),
  NULL(50404, "系统错误 NULL"),
  ;

  /** 返回码 */
  private int code;
  /** 消息 */
  private String message;
}
