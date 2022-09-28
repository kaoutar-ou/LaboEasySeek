//package org.irisi.laboeasyseek.services;
//
//import jakarta.faces.application.FacesMessage;
//import jakarta.faces.context.FacesContext;
//import jakarta.servlet.http.HttpSession;
//import org.irisi.laboeasyseek.dao.EntityManagerFactorySingleton;
//import org.irisi.laboeasyseek.dao.UserRepository;
//import org.irisi.laboeasyseek.entities.User;
//import org.irisi.laboeasyseek.utils.SessionUtils;
//
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.PBEKeySpec;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityTransaction;
//import java.math.BigInteger;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.KeySpec;
//import java.util.Objects;
//
//public class UserService implements UserRepository {
//    private EntityManager em;
//    private EntityTransaction et;
//
//    public UserService() {
//        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
//        et = em.getTransaction();
//    }
//
//    @Override
//    public User save(User user) {
//        System.out.println(user);
//            try {
//                et.begin();
////                newUser.setId(1L);
////                user.setPassword("getSecurePassword(user.getPassword())");
//                System.out.println(user);
//                em.persist(user);
//                et.commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//                et.rollback();
//            }
//            return user;
//    }
//
//
//    @Override
//    public User getUserByEmail(String email) {
//        User user = null;
//        try {
//            et.begin();
//            user = em.find(User.class, email);
//            et.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            et.rollback();
//        }
//        return user;
//    }
//
//    public Boolean login(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        User dbUser = getUserByEmail(user.getEmail());
//        if (dbUser != null) {
//            if (validatePassword(user.getPassword(), dbUser.getPassword())) {
//                HttpSession session = SessionUtils.getSession();
//                session.setAttribute("email", user.getEmail());
//                return true;
//            }
//        }
//        FacesContext.getCurrentInstance().addMessage(
//                null,
//                new FacesMessage(FacesMessage.SEVERITY_WARN,
//                        "Incorrect Email and Passowrd",
//                        "Please enter correct Email and Password"));
//        return false;
//    }
//
//    public Boolean validatePassword(String typedPassword, String dbPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        return Objects.equals(getSecurePassword(typedPassword), dbPassword);
//    }
//
//    private String getSecurePassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//
//        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//
//        byte[] bayteHash = factory.generateSecret(spec).getEncoded();
//
//        BigInteger bi = new BigInteger(1, bayteHash);
//        String passwordHash = bi.toString(16);
//
//        int paddingLength = (bayteHash.length * 2) - passwordHash.length();
//        if (paddingLength > 0) {
//            return String.format("%0" + paddingLength + "d", 0) + passwordHash;
//        } else {
//            return passwordHash;
//        }
//    }
//
//    public Boolean logout() {
//        HttpSession session = SessionUtils.getSession();
//        session.invalidate();
//        return true;
//    }
//}
