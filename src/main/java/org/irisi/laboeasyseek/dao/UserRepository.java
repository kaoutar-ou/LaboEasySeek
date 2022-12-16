package org.irisi.laboeasyseek.dao;


import org.irisi.laboeasyseek.models.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface UserRepository {
    User save(User user);
    User findUserByEmail(String email);

    String getSecurePassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
}