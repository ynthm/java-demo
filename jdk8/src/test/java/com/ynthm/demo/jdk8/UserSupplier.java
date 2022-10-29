package com.ynthm.demo.jdk8;

import com.ynthm.demo.jdk8.bean.User;

import java.util.Random;
import java.util.function.Supplier;

public class UserSupplier implements Supplier<User> {

  private int index = 0;
  private final Random random = new Random();

  @Override
  public User get() {
    User user = new User().setId(index++).setName("name_" + index).setAge(random.nextInt(30));
    user.setBalance(random.nextLong());

    return user;
  }
}
