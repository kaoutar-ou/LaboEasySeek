package org.irisi.laboeasyseek.services;

import jakarta.ejb.Remote;
import org.irisi.laboeasyseek.models.Post;
import org.irisi.laboeasyseek.models.User;

import java.util.List;

@Remote
public interface IPostService {

    public Boolean addPost(Post post, String category);
    public String deletePost();
    public String updatePost();
    public String getPost();
    public List<Post> getAllPosts(String search, String searchCategory, String searchTag);
}
