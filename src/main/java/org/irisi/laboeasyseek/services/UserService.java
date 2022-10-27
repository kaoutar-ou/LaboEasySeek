package org.irisi.laboeasyseek.services;

import com.mongodb.client.MongoCollection;
import jakarta.servlet.http.HttpSession;
import org.irisi.laboeasyseek.configuration.DBConfig;
import org.irisi.laboeasyseek.models.User;
import org.irisi.laboeasyseek.utils.SessionUtils;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

public class UserService {

    MongoCollection<User> userCollection = DBConfig.getUserCollection();

    public Boolean login(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User dbUser = findUserByEmailOrUsername(user.getEmail());


        if (dbUser != null) {
            if (validatePassword(user.getPassword(), dbUser.getPassword())) {
                System.out.println("dbUser.getPassword()");
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("email", user.getEmail());
                session.setAttribute("username", user.getUsername());
                return true;
            }
        }
//        FacesContext.getCurrentInstance().addMessage(
//                null,
//                new FacesMessage(FacesMessage.SEVERITY_WARN,
//                        "Incorrect Email and Passowrd",
//                        "Please enter correct Email and Password"));
        return false;
    }


    public User findUserByEmailOrUsername(String input) {
        User user = null;

        user = userCollection.find(
                or(
                        eq("email", input),
                        eq("username", input)
                )
        ).first();

        return user;
    }

    public Boolean validatePassword(String password, String passwordHash) {
        return password.equals(passwordHash);
    }

}
