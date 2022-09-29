package org.irisi.laboeasyseek.controllers;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.xml.bind.JAXBException;
import org.irisi.laboeasyseek.entities.Post;
import org.irisi.laboeasyseek.services.XMLService;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Named("xmlController")
@SessionScoped
public class XMLController implements Serializable {

    private static final long serialVersionUID = -5433850275007435405L;

//    private static final String fileLocation = "./root.xml";

    private String searchString = "";

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Post> getAllPosts() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        List<Post> posts;
        if (Objects.equals(searchString, "")) {
            posts = XMLService.getAllPosts();
        }
        else {
            posts = XMLService.searchPostsByContent(searchString);
        }
        return posts;
    }

    public void searchPostsByContent() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        setSearchString(searchString);
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Incorrect Username and Passowrd",
                        "Please enter correct username and Password"));
//        return posts;
    }

//    public List<Post> searchPostsByContent() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
//        List<Post> posts = XMLService.searchPostsByContent(searchString);
//        return posts;
//    }

    public void addPost(Post post) throws JAXBException {

        XMLService.addPost(post);
    }




}
