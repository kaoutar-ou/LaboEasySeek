package org.irisi.laboeasyseek.dao;

import org.irisi.laboeasyseek.entities.Userr;

public interface UserRepository {
    Userr save(Userr user);
    Userr findUserByEmail(String email);
}
