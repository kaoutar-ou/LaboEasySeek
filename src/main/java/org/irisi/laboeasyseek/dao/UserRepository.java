package org.irisi.laboeasyseek.dao;


import org.irisi.laboeasyseek.models.User;

public interface UserRepository {
    User save(User user);
    User findUserByEmail(String email);
}