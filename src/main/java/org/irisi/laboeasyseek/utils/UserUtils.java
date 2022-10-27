package org.irisi.laboeasyseek.utils;

public class UserUtils {
    public Boolean validatePassword(String password, String passwordHash) {
        return password.equals(passwordHash);
    }
}
