package com.ynthm.json.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.annotation.PostConstruct;

/** @author ethan */
public class CustomObjectMapper extends ObjectMapper {
  private static final long serialVersionUID = 7353541644647901308L;

  @PostConstruct
  public void customConfig() {
    // Uses Enum.toString() for serialization of an Enum
    this.enable(SerializationFeature.WRITE_ENUM_KEYS_USING_INDEX);
    // Uses Enum.toString() for deserialization of an Enum
    this.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
  }
}
