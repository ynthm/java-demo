package com.ynthm.tools;

import com.ynthm.tools.domain.User;

/**
 * Author : Ynthm
 */
public class UserServiceImpl implements UserService {

    @Override
    public boolean saveUser(User user) {
        System.out.println("保存用户: " + user.getName());
        return true;
    }
}
