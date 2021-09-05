package com.ynthm.tools.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author : Ethan Wang */
public class JsonTest {

  public static void main(String[] args) throws Exception {
    User user = new User();
    user.setAge(18);
    user.setName("Ethan");
    user.setBirthday(LocalDate.of(1990, 8, 4));
    user.setEmail("iwys@qq.com");
    ObjectMapper mapper = new ObjectMapper();
    System.out.println(mapper.writeValueAsString(user));

    String jsonString = "{\"id\":1,\"name\":\"2\"}";
    JavaType javaType =
        mapper.getTypeFactory().constructMapLikeType(HashMap.class, String.class, String.class);
    Map<String, String> map = mapper.readValue(jsonString, javaType);
    Map<String, String> map1 =
        mapper.readValue(jsonString, new TypeReference<Map<String, String>>() {});
    System.out.println(map);
    System.out.println(map1);

    String jsonList = "[{\"age\": 1, \"name\": \"Ethan\"}]";
    javaType = mapper.getTypeFactory().constructParametricType(List.class, User.class);
    List<User> list = mapper.readValue(jsonList, javaType);
    List<User> list1 = mapper.readValue(jsonList, new TypeReference<List<User>>() {});
    System.out.println(list);
    System.out.println(list1);
  }
}
