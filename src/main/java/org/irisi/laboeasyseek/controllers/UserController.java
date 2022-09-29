package org.irisi.laboeasyseek.controllers;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.irisi.laboeasyseek.entities.Userr;
import org.irisi.laboeasyseek.services.UserrService;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Named("userController")
@SessionScoped
public class UserController implements Serializable {
    UserrService userrService;

    public UserController () {
        userrService = new UserrService();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        User user = new User();
////        UserService userService1 = new UserService();
////        user.setId(2L);
//        user.setEmail("kaoutar@gmail.com");
//        user.setPassword("123456789k");
////        userService1.save(user);
//        Post post = new Post();
//        post.setName("kkkk");
//        PostService postService = new PostService();
//        postService.save(user);

        UserrService userrService = new UserrService();

       //  add user test
        Userr user = new Userr();
        user.setEmail("abdelhadi2@gmail.com");
        user.setPassword("896618abdelhadi");
        userrService.save(user);


        // find user test
//        Userr userr = userrService.findUserByEmail("kaoutar4@gmail.com");
//        System.out.println(userr.getEmail());
//        System.out.println(userr.getPassword());


//        User user = new User();
//        user.setEmail("kaoutar@gmail.com");
//        user.setPassword("123456789k");
//        UserService userService = new UserService();
//        userService.save(user);


        // login test
//        Userr user = new Userr();
//        user.setEmail("kaoutar4@gmail.com");
//        user.setPassword("123586789k");
//        System.out.println(userrService.login(user));

    }

    public String addUser(Userr user) {
        Userr newUser = null;
        newUser = userrService.save(user);
        if (newUser != null) return "created";
        return "error";
    }

    public String login(Userr user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        Userr userr = new Userr();
//        userr.setEmail(user.getEmail());
//        userr.setPassword(user.getPassword());
        userr.setEmail("kaoutar4@gmail.com");
        userr.setPassword("123456789k");
        System.out.println("------------------------------hihihihi------------------------------------------");

                boolean loggedIn = userrService.login(user);
                System.out.println(loggedIn);
        if (loggedIn) {
            return "loggedIn";
        }
        return "logIn";

//        return  "loggedIn";
    }
//
//    public String logout() {
//        if (userService.logout()) {
//            return "logIn";
//        }
//        return "test";
//    }
}
