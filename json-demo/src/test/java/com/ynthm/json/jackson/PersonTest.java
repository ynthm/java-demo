package com.ynthm.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class PersonTest {

  @Test
  void test1() throws JsonProcessingException {

    ObjectMapper om = new ObjectMapper();
    om.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    String personStr = "{\"firstName\":\"Ethan\",\"lastName\":\"Wang\",\"love\":\"chili\"}";
    Person person = om.readValue(personStr, Person.class);
    String s = om.writeValueAsString(person);
    assertThat(s).contains("last_name");
    System.out.println(s);
  }
}
