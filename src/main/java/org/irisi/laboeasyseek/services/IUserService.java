package org.irisi.laboeasyseek.services;

import jakarta.ejb.Remote;
import org.irisi.laboeasyseek.models.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@Remote
public interface IUserService {

    public Boolean login(User user) throws NoSuchAlgorithmException, InvalidKeySpecException;
    public String addUser();
    public String deleteUser();
    public String updateUser();
    public String getUser();
    public String getAllUsers();


}
