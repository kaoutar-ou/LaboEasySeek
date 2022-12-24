package org.irisi.laboeasyseek.ws;

import com.rabbitmq.client.Return;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import org.irisi.laboeasyseek.dao.EntityManagerFactorySingleton;
import org.irisi.laboeasyseek.dao.GenericDao;
import org.irisi.laboeasyseek.models.Conversation;
import org.irisi.laboeasyseek.models.Message;
import org.irisi.laboeasyseek.models.Topic;
import org.irisi.laboeasyseek.models.User;
import org.irisi.laboeasyseek.services.IUserService;
import org.irisi.laboeasyseek.services.UserService;
import org.irisi.laboeasyseek.services.UserServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;


@Stateless
@LocalBean
@Path("/jms")
public class JmsWS {

    private EntityManager em;
    private EntityTransaction et;



    public JmsWS() {
        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
        et = em.getTransaction();
    }



    @GET
    @Path("/topic")
    @Produces("application/json")
    public Topic sayHello() {
        Topic topic = new Topic();
        topic.setName("Hello");
        return topic;
    }

    @GET
    @Path("/test")
    @Consumes("application/json")
    public String postTopic(@QueryParam("message") String message, @QueryParam("sender") Long sender,
                            @QueryParam("receiver") Long receiver, @QueryParam("conversation") Long conversation) {

        System.out.println("Message : " + message + " Sender : " + sender + " Receiver : " + receiver + " Conversation : " + conversation);

        try {
            et.begin();
            User msgSender = em.find(User.class, sender);
            User msgReceiver = em.find(User.class, receiver);
            ArrayList<User> users = new ArrayList<>();
            users.add(msgSender);
            users.add(msgReceiver);
            Conversation msgConversation = null;
            if(conversation != null) {
                System.out.println("Conversation id 1 : " + conversation);
                msgConversation = em.find(Conversation.class, conversation);
            }
            if (msgConversation == null) {
                msgConversation = new Conversation();
                msgConversation.setUsers(users);
//                msgSender.getConversations().add(msgConversation);
//                msgReceiver.getConversations().add(msgConversation);
                em.persist(msgConversation);
            }
            Message msg = new Message();

            System.out.println("Conversation id 2 : " + msgConversation.getId());
            msg.setContent(message);
            msg.setSender(msgSender);
            msg.setReceiver(msgReceiver);
            msg.setConversation(msgConversation);
            em.persist(msg);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("message " + message);
        System.out.println("sender " + sender);
        System.out.println("receiver " + receiver);
        System.out.println("conversation " + conversation);

        return message + " " + sender + " " + receiver + " " + conversation;
    }


}