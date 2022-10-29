package com.ynthm.json.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ynthm.json.common.Result;

import java.lang.reflect.Type;
import java.util.List;

/** @author ethan */
public class GsonUtil {
  /** 不过滤空值 */
  private static final Gson GSON = new GsonBuilder().serializeNulls().create();

  public static <T> Result<T> fromJsonObject(String json, Class<T> clazz) {
    Type type = new ParameterizedTypeImpl(Result.class, new Class[] {clazz});
    return GSON.fromJson(json, type);
  }

  public static <T> Result<List<T>> fromJsonArray(String json, Class<T> clazz) {
    // 生成List<T> 中的 List<T>
    Type listType = new ParameterizedTypeImpl(List.class, new Class[] {clazz});
    // 根据List<T>生成完整的Result<List<T>>
    Type type = new ParameterizedTypeImpl(Result.class, new Type[] {listType});
    return GSON.fromJson(json, type);
  }
}
