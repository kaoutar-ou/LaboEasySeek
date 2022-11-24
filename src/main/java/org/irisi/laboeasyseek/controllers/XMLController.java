package org.irisi.laboeasyseek.controllers;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;
import jakarta.xml.bind.JAXBException;
import org.irisi.laboeasyseek.services.XMLService;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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

    public List<Post> getPosts() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        posts = getAllPosts();
        pages = posts.size() / 3;
        if (posts.size() % 3 != 0) {
            pages++;
        }
        if (page < pages) {
            posts = posts.subList(page*3, (page+1)*3);
        }
        else {
            posts = posts.subList(page*3, posts.size());
        }
//        posts = posts.subList(3,5);
//        postsSize = posts.size();
//        int lastIndex = index + 3;
//        if (postsSize - index < 3) {
//            lastIndex = postsSize;
//        }
//        posts = posts.subList(index,lastIndex);
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    private List<Post> posts;

    private int pages = 0;

    private int page = 0;

//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }
//
//    private int index = 0;
//
//    private int postsSize = 0;

    public void processPreviousAction( ActionEvent event ) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
//        if (index > 0) {
//            index = index--;
//        }
        if (page > 0) {
            page--;
        }
    }
    public void processNextAction( ActionEvent event ) {
//
//        System.out.println("size 1 : "+posts.size());
//        System.out.println("size 2 : "+ (index +3));
////        if (index + 3 < postsSize) {
////            index ++;
////        }
//        if (index + 3 < postsSize) {
//            index = index + 3;
//        }
        if (page < pages) {
            page++;
        }
    }



}
