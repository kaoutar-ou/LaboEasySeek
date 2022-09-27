package org.irisi.laboeasyseek.controllers;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.xml.bind.JAXBException;
import org.irisi.laboeasyseek.entities.Post;
import org.irisi.laboeasyseek.services.XMLService;

import java.io.Serializable;

@Named("postController")
@SessionScoped
public class PostController implements Serializable {

    private static final long serialVersionUID = -5433850275008415405L;

    public void addPost() throws JAXBException {
        System.out.println("hi");
        Post post = new Post();
        post.setId(147L);
        post.setName("fffff");
        XMLService.addPost(post);
    }
}
