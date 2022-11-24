package org.irisi.laboeasyseek.services;


import org.irisi.laboeasyseek.dao.EntityManagerFactorySingleton;
import org.irisi.laboeasyseek.dao.PostRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class PostService implements PostRepository {

    private EntityManager em;
    private EntityTransaction et;
    public PostService() {
        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
        et = em.getTransaction();
    }

    @Override
    public Post save(Post cp) {
        try {
            et.begin( );
            em.persist(cp);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace( );
            et.rollback();
        }
        return cp;
    }

//    @Override
//    public void save(User user) {
//        System.out.println(user);
//        try {
//            et.begin();
//            System.out.println(user);
//            em.persist(user);
//            et.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            et.rollback();
//        }
//    }

}
