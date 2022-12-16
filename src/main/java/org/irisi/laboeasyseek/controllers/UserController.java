package org.irisi.laboeasyseek.controllers;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.irisi.laboeasyseek.models.User;
import org.irisi.laboeasyseek.services.IUserService;

import jakarta.ejb.EJB;
import java.io.Serializable;

@SessionScoped
@Named("userController")
public class UserController implements Serializable {

    @EJB
    private IUserService userService;

    public UserController(String[] args) {
    }

    public UserController() {
    }

    public String login(User user) {
        System.out.println("login");
        try {
            if (userService.login(user)) {
                return "home.xhtml?faces-redirect=true";
            } else {
                return "login.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "login.xhtml?faces-redirect=true";
        }
//        if(userService.login(user)) {
//            System.out.println("login success");
//            return "home";
//        }
//        System.out.println("login failed");
//        return "login";
    }



    public String addUser() {
        return null;
    }

    public String deleteUser() {
        return null;
    }

    public String updateUser() {
        return null;
    }

    public String getUser() {
        return null;
    }

    public String getAllUsers() {
        return null;
    }
}