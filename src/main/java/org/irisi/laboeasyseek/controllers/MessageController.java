package org.irisi.laboeasyseek.controllers;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.irisi.laboeasyseek.beans.MessageBean;
import org.irisi.laboeasyseek.models.Conversation;
import org.irisi.laboeasyseek.models.Message;
import org.irisi.laboeasyseek.services.IMessageService;
import org.irisi.laboeasyseek.services.IPostService;
import org.irisi.laboeasyseek.services.MessageServiceImpl;
import org.irisi.laboeasyseek.utils.SessionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named("messageController")
public class MessageController implements Serializable {
    @EJB
    private IMessageService messageService;

    private String sender;
    private String receiver;
    private String destination;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String handleSetReceiver(String receiver) {
        this.receiver = receiver;
        System.out.println("RECEIVER : " + receiver);
        System.out.println("SENDER : " + sender);
        Conversation conv = messageService.getConversation(Long.parseLong(sender), Long.parseLong(receiver));
        if (conv != null) {
            System.out.println("CONV : " + conv.getId());
            this.destination = conv.getId().toString();
        } else {
            this.destination = "0";
        }
        messageBean = new MessageBean();
        messageBean.setUser(sender);
        messageBean.setSender(sender);
        messageBean.setPassword(sender);
        messageBean.setReceiver(receiver);
        messageBean.setDestination(destination);
        messageBean.setClientId(sender);
        messageBean.setMessage("");
        return "chatPage.xhtml?faces-redirect=true";
    }

    public MessageController(String[] args) {
        this.sender = SessionUtils.getUserId();
        assert sender != null;
        this.destination = messageService.getConversation(Long.parseLong(sender), Long.parseLong(receiver)).getId().toString();
    }

    private MessageBean messageBean;

    public MessageController() {
        this.sender = SessionUtils.getUserId();
        assert sender != null;
        if (messageService == null) {
            messageService = new MessageServiceImpl();
        }
    }



    public MessageBean getMessageBean() {
        return messageBean;
    }

    public void setMessageBean(MessageBean messageBean) {
        this.messageBean = messageBean;
    }

    public void sendMessage() {
        System.out.println("MESSAGE7 : " + messageBean.getMessage());
        messageService.addMessage(messageBean);
    }

    List<Message> messages = new ArrayList<>();

    public List<Message> getMessages() {
        if (messages.isEmpty()) {
            handleGetAllMessages();
        }
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void handleGetAllMessages() {
        List<Message> messageList = messageService.getAllMessages(Long.parseLong(receiver));
        setMessages(messageList);
    }


}
