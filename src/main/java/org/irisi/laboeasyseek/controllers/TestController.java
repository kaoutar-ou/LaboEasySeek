package org.irisi.laboeasyseek.controllers;

import org.irisi.laboeasyseek.dao.EntityManagerFactorySingleton;
import org.irisi.laboeasyseek.dao.UserRepository;
import org.irisi.laboeasyseek.models.Category;
import org.irisi.laboeasyseek.models.User;
import org.irisi.laboeasyseek.services.UserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TestController {

    private static EntityManager em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
    private static EntityTransaction et = em.getTransaction();

    public TestController() {
        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
        et = em.getTransaction();
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");

//        User user = new User();
//        UserRepository userRepository = new UserService();
//        user.setUsername("test");
//        user.setPassword("test123456789");
//        user.setEmail("test@gmail.com");
//        userRepository.save(user);


//
//        Category article = new Category();
//        article.setName("article");
//
//        Category report = new Category();
//        report.setName("report");
//
//        Category event = new Category();
//        event.setName("event");
//
//        Category other = new Category();
//        other.setName("other");
//
//        try {
//            TestController.et.begin();
//            TestController.em.persist(article);
//            TestController.em.persist(report);
//            TestController.em.persist(event);
//            TestController.em.persist(other);
//            TestController.et.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
