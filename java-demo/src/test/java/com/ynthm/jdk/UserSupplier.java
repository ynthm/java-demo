package com.ynthm.jdk;

import com.ynthm.tools.domain.User;

import java.util.Random;
import java.util.function.Supplier;

public class UserSupplier implements Supplier<User> {

    private int index = 0;
    private final Random random = new Random();

    @Override
    public User get() {
        return new User(index++, "name_" + index, random.nextInt(100));
    }
}
