package com.ynthm.jdk;

import com.ynthm.tools.domain.Role;
import com.ynthm.tools.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalTest {

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
        role.setType("admin");
        User user = User.builder().role(role).build();
        System.out.println(isCheckUser(user));

    }

    private void println(String text) {
        Optional.ofNullable(text).ifPresent(System.out::println);
    }

    private int getLength(String text) {
        return Optional.ofNullable(text).map(String::length).orElse(-1);

    }

    public String isCheckUser(User user) {
        return Optional.ofNullable(user)
                .map(u -> u.getRole())
                .map(p -> p.getType())
                .orElse("用户为空");
    }
}
