package com.newsportal.dao.interfaces;

import com.newsportal.entity.User;

public interface UserDao {
    User findByEmail(String email);
    User findById(Long id);
    void save(User user);
    void update(User user);
    void delete(User user);
    boolean verifyPassword(Long userId, String password);
    boolean emailExists(String email);
}