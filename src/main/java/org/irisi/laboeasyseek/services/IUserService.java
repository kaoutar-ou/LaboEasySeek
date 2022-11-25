package org.irisi.laboeasyseek.services;

import jakarta.ejb.Remote;
import org.irisi.laboeasyseek.models.User;


@Remote
public interface IUserService {

    public Boolean login(User user);
    public String addUser();
    public String deleteUser();
    public String updateUser();
    public String getUser();
    public String getAllUsers();


}
