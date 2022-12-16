package org.irisi.laboeasyseek.services;

import jakarta.ejb.Remote;
import org.irisi.laboeasyseek.beans.MessageBean;
import org.irisi.laboeasyseek.models.Message;
import org.irisi.laboeasyseek.models.Post;

import java.util.List;

@Remote
public interface IMessageService {
    public Boolean addMessage(MessageBean messageBean);
    public List<Message> getAllMessages(Long receiverId);
}
