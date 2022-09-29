package org.irisi.laboeasyseek.controllers;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.xml.bind.JAXBException;
import org.irisi.laboeasyseek.entities.Post;
import org.irisi.laboeasyseek.services.XMLService;

import java.io.Serializable;

@Named /*("loginBean")*/
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = -5433850275008415405L;
    private String login = "James";
    private String password = "007";

    public String getLogin() {
        System.out.println( "in getLogin" ); return login;
    }
    public void setLogin(String login) {
        System.out.println( "in setLogin with " + login );
        this.login = login;
    }
    public String getPassword() {
        System.out.println( "in getPassword" );
        return password;
    }
    public void setPassword(String password) {
        System.out.println( "in setPassword with " + password );
        this.password = password;
    }
    public String returnAction() throws JAXBException {
        System.out.println( "in returnAction" );
        System.out.println("hi");
        Post post = new Post();
        post.setId(147L);
        post.setTitle("fffff");
        XMLService.addPost(post);
        return password.equals( "007" ) ? "success" : "failure";
    }

    public String testLogin() {
        return "logIn";
    }
    public String testAddUser() {
        return "addUser";
    }
}