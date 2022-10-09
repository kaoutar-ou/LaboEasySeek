package org.irisi.laboeasyseek.services;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import jakarta.servlet.http.HttpSession;
import org.irisi.laboeasyseek.dao.EntityManagerFactorySingleton;
import org.irisi.laboeasyseek.dao.UserRepository;
import org.irisi.laboeasyseek.entities.Userr;
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

public class UserrService implements UserRepository {
    private EntityManager em;
    private EntityTransaction et;

    public UserrService() {
        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
        et = em.getTransaction();
    }

    @Override
    public Userr save(Userr user) {
        Userr newUser = new Userr();
        try {
            et.begin();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(getSecurePassword(user.getPassword()));
            System.out.println(user);
            System.out.println(newUser);
//            user.setPassword(getSecurePassword(user.getPassword()));
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
    public Userr findUserByEmail(String email) {
        Userr user = null;
        try {
            et.begin();
//            user = em.createQuery(
//                    "SELECT u from Userr u WHERE u.email LIKE :email", Userr.class)
//                    .setParameter("email", email)
//                    .getSingleResult();
//            user = em.createQuery("select u from Userr u where u.email like :email", Userr.class)
//                            .setParameter("email", email)
//                            .getSingleResult();

            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Userr> criteria = builder.createQuery(Userr.class);
            Root<Userr> root = criteria.from(Userr.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get("email"), email));
            TypedQuery<Userr> typed = em.createQuery(criteria);
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


    public Boolean login(Userr user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Userr dbUser = findUserByEmail(user.getEmail());


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
