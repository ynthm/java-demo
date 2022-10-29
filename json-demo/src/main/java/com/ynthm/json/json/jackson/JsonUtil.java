package com.ynthm.json.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class JsonUtil {
  private static ObjectMapper OM = new ObjectMapper();

  static {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    OM.setDateFormat(dateFormat);
    OM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    OM.enable(SerializationFeature.INDENT_OUTPUT);
    OM.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    OM.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false);
    OM.disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
    OM.disable(SerializationFeature.CLOSE_CLOSEABLE);
    OM.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    OM.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    OM.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
  }

  public static String toJson(Object o) {
    try {
      return OM.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }

  public static <T> T toObject(String data, Class<T> cls) {
    try {
      return OM.readValue(data, cls);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }

  public static <T> T toArray(String json, Class<?> elementType) {
    ArrayType arrayType = TypeFactory.defaultInstance().constructArrayType(elementType);
    try {
      return OM.readValue(json, arrayType);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }

  public static <T> T jsonToObjectList(
      String json, Class<?> collectionClass, Class<?>... elementClass) {
    T obj = null;
    JavaType javaType = OM.getTypeFactory().constructParametricType(collectionClass, elementClass);
    try {
      obj = OM.readValue(json, javaType);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
    return obj;
  }

  public static <T> T jsonToObjectHashMap(String json, Class<?> keyClass, Class<?> valueClass) {
    T obj = null;
    JavaType javaType =
        OM.getTypeFactory().constructParametricType(HashMap.class, keyClass, valueClass);
    try {
      obj = OM.readValue(json, javaType);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
    return obj;
  }
}
