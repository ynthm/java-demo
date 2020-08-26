package com.ynthm.json.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.List;

class UserTest {
  @Test
  void test() {
    Gson gson = new Gson();
    String json =
        "{\"name\":\"怪盗kidou\",\"age\":24,\"emailAddress\":\"ikidou_1@example.com\",\"email\":\"ikidou_2@example.com\",\"email_address\":\"ikidou_3@example.com\"}";
    User user = gson.fromJson(json, User.class);
    System.out.println(user.getEmailAddress());
  }

  @Test
  void testGe() {
    Gson gson = new Gson();
    String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
    String[] strings = gson.fromJson(jsonArray, String[].class);
    List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {}.getType());
  }
}
