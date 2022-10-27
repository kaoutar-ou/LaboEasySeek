package org.irisi.laboeasyseek.services;

import org.irisi.laboeasyseek.models.User;

public interface IUserService {

    public Boolean login(User user);
    public String addUser();
    public String deleteUser();
    public String updateUser();
    public String getUser();
    public String getAllUsers();
}
