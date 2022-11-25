package org.irisi.laboeasyseek.dao;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton{
    private static final EntityManagerFactory EMF;
    static {
        EMF = Persistence.createEntityManagerFactory("hhh");
    }
    public static EntityManagerFactory getEntityManagerFactory() {
        return EMF;
    }
}