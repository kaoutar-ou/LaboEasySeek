package org.irisi.laboeasyseek.beans;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.irisi.laboeasyseek.models.Comment;
import org.irisi.laboeasyseek.services.IPostService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("postBean")
//@Getter
@Setter
@ToString
public class PostBean implements Serializable {
    private String postCategory = "";

    public String getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(String postCategory) {
        this.postCategory = postCategory;
    }

    private String postTitle = "";

    private String postVersion = "";

    private String postDescription = "";

    private String postContent = "";

    private String postLocation = "";

    private List<Comment> comments = new ArrayList<>();

    private List<String> keywords = new ArrayList<>();

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostVersion() {
        return postVersion;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public String getPostContent() {
        return postContent;
    }

    public String getPostLocation() {
        return postLocation;
    }


    public List<String> getKeywords() {
        return keywords;
    }

    public IPostService getPostService() {
        return postService;
    }

    @EJB
    private IPostService postService;

    public PostBean(String[] args) {
    }

    public PostBean() {
    }
    public List<Comment> getComments(String postId) {
        Long id = Long.parseLong(postId);
        List<Comment> comments = postService.getPostComments(id);
        System.out.println("comments size 1 : " + comments.size());
        System.out.println("comments 2: " + comments);
        setComments(comments);
        return comments;
    }
}
