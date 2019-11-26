package com.ynthm.tools.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Author : Ynthm
 */

@Data
@SuperBuilder
public class User {
    private int id;
    private String name;
    private int age;
    private Role role;

    public User() {

    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.id = id;
    }
}
