package com.newsportal.service;

import com.newsportal.dao.interfaces.UserDao;
import com.newsportal.dto.AuthDto;
import com.newsportal.entity.User;

public class AuthService {
    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User authenticate(AuthDto authDto) {
        User user = userDao.findByEmail(authDto.getEmail());
        if (user != null && user.getPassword().equals(authDto.getPassword())) {
            return user;
        }
        return null;
    }
}