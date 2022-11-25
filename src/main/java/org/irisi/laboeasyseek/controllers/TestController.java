package org.irisi.laboeasyseek.controllers;

import org.irisi.laboeasyseek.dao.UserRepository;
import org.irisi.laboeasyseek.models.User;
import org.irisi.laboeasyseek.services.UserService;

public class TestController {


    public static void main(String[] args) {
        System.out.println("Hello World!");

        User user = new User();
        UserRepository userRepository = new UserService();
        user.setUsername("test");
        user.setPassword("test123456789");
        user.setEmail("test@gmail.com");
        userRepository.save(user);

    }
}
