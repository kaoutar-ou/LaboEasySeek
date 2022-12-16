package org.irisi.laboeasyseek.services;

import jakarta.ejb.Stateless;
import jakarta.servlet.http.HttpSession;
import org.irisi.laboeasyseek.dao.UserRepository;
import org.irisi.laboeasyseek.models.User;
import org.irisi.laboeasyseek.utils.SessionUtils;
import org.irisi.laboeasyseek.utils.UserUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Stateless
public class UserServiceImpl implements IUserService {



    public Boolean login (User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserRepository userDao = new UserService();
        UserUtils userUtils = new UserUtils();

        System.out.println("loginService");
        User dbUser = null;
        dbUser = userDao.findUserByEmail(user.getEmail());

        System.out.println("dbUser: " + user.getPassword());

        if (dbUser != null) {
            System.out.println("dbUser: " + dbUser);
            System.out.println("dbUser: " + dbUser.getPassword());
            if (userUtils.validatePassword(userDao.getSecurePassword(user.getPassword()), dbUser.getPassword())) {
                System.out.println("dbUser");
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("email", dbUser.getEmail());
                session.setAttribute("username", dbUser.getUsername());
                session.setAttribute("userId", dbUser.getId());
                System.out.println("dbUser" + dbUser.getId());
                System.out.println("dbUser" + dbUser.getEmail());
                System.out.println("dbUser" + dbUser.getUsername());
                return true;
            }
        }
        return false;
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
