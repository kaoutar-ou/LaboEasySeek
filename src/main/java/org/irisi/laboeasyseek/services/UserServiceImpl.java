package org.irisi.laboeasyseek.services;

import jakarta.ejb.Stateless;
import jakarta.servlet.http.HttpSession;
import org.irisi.laboeasyseek.dao.UserDao;
import org.irisi.laboeasyseek.models.User;
import org.irisi.laboeasyseek.utils.SessionUtils;
import org.irisi.laboeasyseek.utils.UserUtils;

@Stateless
public class UserServiceImpl implements IUserService {



    public Boolean login (User user) {
        UserDao userDao = new UserDao();
        UserUtils userUtils = new UserUtils();

        System.out.println("loginService");
        User dbUser = null;
        dbUser = userDao.findUserByEmailOrUsername(user.getEmail());

        System.out.println("dbUser: " + user.getPassword());

        if (dbUser != null) {
            System.out.println("dbUser: " + dbUser);
            System.out.println("dbUser: " + dbUser.getPassword());
            if (userUtils.validatePassword(user.getPassword(), dbUser.getPassword())) {
                System.out.println("dbUser");
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("email", user.getEmail());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("userId", user.getId());
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
