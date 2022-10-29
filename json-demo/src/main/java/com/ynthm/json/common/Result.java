package com.ynthm.json.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统一返回结果
 *
 * @author ethan
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Result<T> {
  private static final long serialVersionUID = 1L;
  /**
   * 结果编码
   *
   * @mock 0
   */
  private int code;
  /** 提示消息 */
  private String message;
  /** 返回数据 */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T data;

  private Result() {
    // to do nothing
  }

  public static <T> Result<T> ok(T data) {
    return restResult(data, ResultCode.OK.getCode(), ResultCode.OK.getMessage());
  }

  public static <T> Result<T> ok() {
    return restResult(null, ResultCode.OK.getCode(), ResultCode.OK.getMessage());
  }

  public static <T> Result<T> warn(String message) {
    return Result.restResult(null, ResultCode.WARN.getCode(), message);
  }

  public static <T> Result<T> error(ResultCode errorCode) {
    return Result.restResult(null, errorCode.getCode(), errorCode.getMessage());
  }

  public static <T> Result<T> errorMessage(ResultCode resultCode, String message) {
    return Result.restResult(null, resultCode.getCode(), message);
  }

  private static <T> Result<T> restResult(T data, int code, String msg) {
    Result<T> apiResult = new Result<>();
    apiResult.setCode(code);
    apiResult.setData(data);
    apiResult.setMessage(msg);
    return apiResult;
  }

  public String toJson() throws JsonProcessingException {
    ObjectMapper om = new ObjectMapper();
    return om.writeValueAsString(this);
  }

  public boolean success() {
    return ResultCode.OK.getCode() == code;
  }
}
