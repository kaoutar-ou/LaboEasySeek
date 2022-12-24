package org.irisi.laboeasyseek.services;

import jakarta.ejb.Stateful;
import org.irisi.laboeasyseek.beans.MessageBean;
import org.irisi.laboeasyseek.dao.EntityManagerFactorySingleton;
import org.irisi.laboeasyseek.dao.GenericDao;
import org.irisi.laboeasyseek.models.Conversation;
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
        messageGenericDao = new GenericDao<Message>(Message.class);
        conversationGenericDao = new GenericDao<Conversation>(Conversation.class);
    }

    private GenericDao<Message> messageGenericDao;

    private GenericDao<Conversation> conversationGenericDao;


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

    @Override
    public Conversation getConversation(Long senderId, Long receiverId) {
        Conversation conversation = null;

        try {
            et.begin();
            List<Conversation> conversations = conversationGenericDao.findAll();
            for (Conversation c : conversations) {
                if ((c.getMessages().size() > 0 && c.getMessages().get(0).getSender().getId().equals(senderId) && c.getMessages().get(0).getReceiver().getId().equals(receiverId))
                        || (c.getMessages().size() > 0 && c.getMessages().get(0).getSender().getId().equals(receiverId) && c.getMessages().get(0).getReceiver().getId().equals(senderId))) {
                    conversation = c;
                    break;
                }
//                if (c.getUsers().size() > 0 && c.getUsers().contains(em.find(User.class, senderId)) && c.getUsers().contains(em.find(User.class, receiverId))) {
//                    conversation = c;
//                    break;
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conversation;
    }

    @Override
    public Conversation getConversation(Long conversationId) {
        Conversation conversation = null;
        try {
            et.begin();
            conversation = conversationGenericDao.findById(conversationId);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conversation;
    }

    @Override
    public List<Conversation> getAllConversations(Long userId) {
        List<Conversation> conversations = new ArrayList<>();
        try {
            et.begin();
            List<Conversation> conversationList = conversationGenericDao.findAll();
            for (Conversation conversation : conversationList) {
                if (conversation.getMessages().size() > 0 && (conversation.getMessages().get(0).getSender().getId().equals(userId) || conversation.getMessages().get(0).getReceiver().getId().equals(userId))) {
                    conversations.add(conversation);
                }
            }
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conversations;
    }

    @Override
    public List<Message> getAllConversationMessages(Long conversationId) {
        List<Message> messages = new ArrayList<>();
        try {
            et.begin();
            Conversation conversation = conversationGenericDao.findById(conversationId);
            messages = conversation.getMessages();
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Boolean addConversationMessage(Long conversationId, MessageBean messageBean) {
        try {
            et.begin();
            Conversation conversation = conversationGenericDao.findById(conversationId);
            Message message = new Message();
            User sender = em.find(User.class, Long.parseLong(messageBean.getSender()));
            User receiver = em.find(User.class, Long.parseLong(messageBean.getReceiver()));
            message.setContent(messageBean.getMessage());
            message.setReceiver(receiver);
            message.setSender(sender);
            em.persist(message);
            conversation.getMessages().add(message);
            conversationGenericDao.update(conversation);
            et.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
