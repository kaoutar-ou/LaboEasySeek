package org.irisi.laboeasyseek.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.irisi.laboeasyseek.utils.SessionUtils;

import java.io.Serializable;

@ViewScoped
@Named("messageBean")
@Getter
@Setter
@ToString
public class MessageBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String result = "";
    private String named = "";

    public void namedChanged(AjaxBehaviorEvent event) {
        System.out.println("MESSAGE1 : " + result);
        result = "Hello, you entered " + named;
    }

    public void send(ActionEvent e) {
        System.out.println("MESSAGE2 : " + result);
    }

    public String showResult() {
        System.out.println("MESSAGE3 : " + result);
        return "result";
    }


    public void messageChanged(AjaxBehaviorEvent event) {
        System.out.println("MESSAGE1 : " + message);
        result = message;
    }

    private String message = "";
    private String receiver = "102";
    private String sender = SessionUtils.getUserId();
    private String user = SessionUtils.getUsername();
    private String password = SessionUtils.getUsername();
    private String clientId = SessionUtils.getUsername();
    private String destination = "chat/" + SessionUtils.getUsername() + "/" + receiver;

}
