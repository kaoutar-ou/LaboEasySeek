package org.irisi.laboeasyseek.services;

import jakarta.ejb.Stateful;
import org.irisi.laboeasyseek.beans.MessageBean;
import org.irisi.laboeasyseek.dao.EntityManagerFactorySingleton;
import org.irisi.laboeasyseek.models.Message;
import org.irisi.laboeasyseek.models.Post;
import org.irisi.laboeasyseek.models.User;
import org.irisi.laboeasyseek.utils.SessionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Stateful
public class MessageServiceImpl implements IMessageService, Serializable {

    private EntityManager em;
    private EntityTransaction et;

    public MessageServiceImpl() {
        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
        et = em.getTransaction();
    }

    @Override
    public Boolean addMessage(MessageBean messageBean) {
        try {
            et.begin();
            Message message = new Message();
            User sender = em.find(User.class, Long.parseLong(messageBean.getSender()));
            User receiver = em.find(User.class, Long.parseLong(messageBean.getReceiver()));
            message.setContent(messageBean.getMessage());
            message.setReceiver(receiver);
            message.setSender(sender);
            em.persist(message);
            et.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Message> getAllMessages(Long receiverId) {
        List<Message> messages = new ArrayList<>();
        List<Message> messagesToReturn = new ArrayList<>();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
            Root<Message> root = criteriaQuery.from(Message.class);
            criteriaQuery.select(root);

            TypedQuery<Message> typedQuery = em.createQuery(criteriaQuery);
            messages = typedQuery.getResultList();

            for (Message message : messages) {
                if ((message.getReceiver().getId().equals(receiverId)
                        && message.getSender().getId().equals(Long.parseLong(Objects.requireNonNull(SessionUtils.getUserId()))))
                        || (message.getSender().getId().equals(receiverId)
                        && message.getReceiver().getId().equals(Long.parseLong(Objects.requireNonNull(SessionUtils.getUserId()))))) {
                    messagesToReturn.add(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MESSAGES size : " + messagesToReturn.size());
        return messagesToReturn;
    }
}
