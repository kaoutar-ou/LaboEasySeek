package org.irisi.laboeasyseek.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@SessionScoped
@Named("messageBean1")
@Getter
@Setter
@ToString
public class MessageBean1 implements Serializable {
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
}
