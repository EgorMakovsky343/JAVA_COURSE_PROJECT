package com.newsportal.service;

import com.newsportal.dao.impl.UserDaoImpl;
import com.newsportal.dao.interfaces.UserDao;
import com.newsportal.entity.User;
import java.time.LocalDate;

public class UserService {
    private final UserDao userDao = new UserDaoImpl();

    public boolean emailExists(String email) {
        return userDao.emailExists(email);
    }

    public User registerUser(String email, String password, String userName, String birthDateStr, String gender, String interests, String role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // Без хеширования
        user.setUserName(userName);
        user.setAdmin("admin".equals(role));
        user.setGender(gender);
        user.setInterests(interests);
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            user.setDateOfBirth(LocalDate.parse(birthDateStr));
        }
        userDao.save(user);
        return user;
    }

    public User findById(Long id) {
        return userDao.findById(id);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public boolean verifyPassword(Long userId, String password) {
        return userDao.verifyPassword(userId, password);
    }
}
