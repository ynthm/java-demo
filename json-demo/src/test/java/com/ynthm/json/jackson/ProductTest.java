package com.ynthm.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class ProductTest {
  private final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  void test() throws JsonProcessingException {
    String productJson = "{\"full_name\":\"Apple TV\"}";
    ObjectMapper objectMapper = new ObjectMapper();
    Product product = objectMapper.readValue(productJson, Product.class);
    String s = objectMapper.writeValueAsString(product);

    assertThat(s).contains("fullName");
  }

  @Test
  void testEnum() throws JsonProcessingException {
    String adminValue = String.valueOf(RoleEnum.ADMIN.getValue());
    String s = MAPPER.writeValueAsString(RoleEnum.ADMIN);
    assertThat(s).isEqualTo(adminValue);

    RoleEnum role = MAPPER.readValue(adminValue, RoleEnum.class);

    assertThat(role).isEqualTo(RoleEnum.ADMIN);
  }

  @Test
  void testCustomObjectMapper() throws JsonProcessingException {
    CustomObjectMapper customObjectMapper = new CustomObjectMapper();
    System.out.println(customObjectMapper.writeValueAsString(Color.RED));
  }
}
