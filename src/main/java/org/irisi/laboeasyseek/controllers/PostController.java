package org.irisi.laboeasyseek.controllers;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.xml.bind.JAXBException;
import org.irisi.laboeasyseek.entities.Post;
import org.irisi.laboeasyseek.entities.UploadManagedBean;
import org.irisi.laboeasyseek.services.UploadHelper;
import org.irisi.laboeasyseek.services.XMLService;

import java.io.Serializable;

@Named("postController")
@SessionScoped
public class PostController implements Serializable {

    private static final long serialVersionUID = -5433850275008415405L;

    public void addPost(Post post, UploadManagedBean uploadManagedBean) throws JAXBException {
        processUpload(post,uploadManagedBean);
        XMLService.addPost(post);
    }

    public void processUpload(Post post, UploadManagedBean uploadManagedBean) {
        System.out.println("file to upload : " + uploadManagedBean.getPart());
        UploadHelper uploadHelper = new UploadHelper();
        post.setPhoto(uploadHelper.processUpload(uploadManagedBean.getPart(),post.getTitle()));
    }
}
