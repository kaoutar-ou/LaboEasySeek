package org.irisi.laboeasyseek.beans;

import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ActionListener;

public class MessageActionListener implements ActionListener {

    @Override
    public void processAction(ActionEvent e)
            throws AbortProcessingException {

        System.out.println("MessageActionListener.processAction()");
        //access userData bean directly
        MessageBean messageBean = (MessageBean) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("messageBean");

        System.out.println(messageBean.getResult());
    }
}