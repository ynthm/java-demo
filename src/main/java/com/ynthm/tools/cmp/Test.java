package com.ynthm.tools.cmp;

import com.ynthm.tools.domain.Book;
import com.ynthm.tools.domain.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Author : Ynthm
 */
public class Test {

    public static void main(String[] args) {
        List<Book> list = new ArrayList<>();
        Collections.sort(list);

        List<User> userList = new ArrayList<>();
        Collections.sort(userList, Comparator.comparingInt(User::getAge));

    }
}
