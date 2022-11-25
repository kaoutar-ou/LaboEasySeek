package org.irisi.laboeasyseek.configuration;


import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.irisi.laboeasyseek.dao.EntityManagerFactorySingleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@WebListener
public class PGConfig implements ServletContextListener {

    private static EntityManager em;
    private static EntityTransaction et;

    public void contextInitialized(ServletContextEvent event) {
        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
        et = em.getTransaction();
        System.out.println("Context initialized");
    }

    public static EntityManager getEm() {
        return em;
    }

    public static EntityTransaction getEt() {
        return et;
    }

    public void contextDestroyed(ServletContextEvent event) {
        // Do stuff during webapp shutdown.
    }
}