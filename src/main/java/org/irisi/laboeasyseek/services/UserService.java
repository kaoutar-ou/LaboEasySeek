package org.irisi.laboeasyseek.services;




import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import jakarta.ejb.Stateless;
import jakarta.servlet.http.HttpSession;
import org.irisi.laboeasyseek.dao.EntityManagerFactorySingleton;
import org.irisi.laboeasyseek.dao.UserRepository;
import org.irisi.laboeasyseek.models.User;
import org.irisi.laboeasyseek.utils.SessionUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Objects;


@Stateless
public class UserService implements UserRepository {
    private EntityManager em;
    private EntityTransaction et;

    public UserService() {
        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
        et = em.getTransaction();

//        em = PGConfig.getEm();
//        et = PGConfig.getEt();
    }

    @Override
    public User save(User user) {
        User newUser = new User();
        try {
            et.begin();
            newUser.setEmail(user.getEmail());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(getSecurePassword(user.getPassword()));

            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(user.getUsername());
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(newUser.getUsername());
//            user.setPassword(getSecurePassword(user.getPassword()))
            em.persist(newUser);
            et.commit();
            return newUser;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;
        try {
            et.begin();
//            user = em.createQuery(
//                    "SELECT u from User u WHERE u.email LIKE :email", User.class)
//                    .setParameter("email", email)
//                    .getSingleResult();
//            user = em.createQuery("select u from User u where u.email like :email", User.class)
//                            .setParameter("email", email)
//                            .getSingleResult();

            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get("email"), email));
            TypedQuery<User> typed = em.createQuery(criteria);
            try {
                user = typed.getSingleResult();
            } catch (final NoResultException nre) {
                return null;
            }

            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return user;
    }


    private String getSecurePassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
//        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] bayteHash = factory.generateSecret(spec).getEncoded();

        BigInteger bi = new BigInteger(1, bayteHash);
        String passwordHash = bi.toString(16);

        int paddingLength = (bayteHash.length * 2) - passwordHash.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + passwordHash;
        } else {
            return passwordHash;
        }
    }


    public Boolean login(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User dbUser = findUserByEmail(user.getEmail());


        if (dbUser != null) {
            if (validatePassword(user.getPassword(), dbUser.getPassword())) {
                System.out.println("dbUser.getPassword()");
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("email", user.getEmail());
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

    public boolean validatePassword(String typedPassword, String dbPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Objects.equals(getSecurePassword(typedPassword), dbPassword);
    }

    public boolean logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return true;
    }
}