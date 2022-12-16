package org.irisi.laboeasyseek.controllers;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.irisi.laboeasyseek.beans.MessageBean;
import org.irisi.laboeasyseek.models.Message;
import org.irisi.laboeasyseek.services.IMessageService;
import org.irisi.laboeasyseek.services.IPostService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("messageController")
public class MessageController implements Serializable {
    @EJB
    private IMessageService messageService;

    public MessageController(String[] args) {
    }

    public MessageController() {
    }

    private MessageBean messageBean = new MessageBean();

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
        List<Message> messageList = messageService.getAllMessages(Long.parseLong("102"));
        setMessages(messageList);
    }
}
