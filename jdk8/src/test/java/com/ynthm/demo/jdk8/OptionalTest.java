package com.ynthm.demo.jdk8;

import com.ynthm.demo.jdk8.bean.Role;
import com.ynthm.demo.jdk8.bean.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class OptionalTest {

  @Test
  void optional() {
    String strA = " abcd";
    println(strA);
    println(null);
    System.out.println(getLength(strA));
    System.out.println(getLength(" "));
    System.out.println(getLength(null));
    System.out.println(isCheckUser(null));
    Role role = new Role();
    role.setName("admin");
    User user = new User().setRole(role);
    System.out.println(isCheckUser(user));
  }

  private void println(String text) {
    Optional.ofNullable(text).ifPresent(System.out::println);
  }

  private int getLength(String text) {
    return Optional.ofNullable(text).map(String::length).orElse(-1);
  }

  public String isCheckUser(User user) {
    return Optional.ofNullable(user).map(u -> u.getRole()).map(p -> p.getName()).orElse("用户为空");
  }
}
