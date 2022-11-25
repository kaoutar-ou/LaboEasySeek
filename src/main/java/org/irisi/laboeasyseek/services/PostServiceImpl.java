package org.irisi.laboeasyseek.services;

import jakarta.ejb.Stateful;
import org.irisi.laboeasyseek.models.Post;

import java.util.ArrayList;
import java.util.List;


@Stateful
public class PostServiceImpl implements IPostService {


    @Override
    public Boolean addPost(Post post, String category) {

        return null;
    }

    @Override
    public String deletePost() {
        return null;
    }

    @Override
    public String updatePost() {
        return null;
    }

    @Override
    public String getPost() {
        return null;
    }


    private int pageIndex = 0;
    private int pageSize = 50;
    private int pagesNumber = 1;

    @Override
    public List<Post> getAllPosts(String search, String searchCategory, String searchTag) {
        List<Post> publicationList = new ArrayList<>();




        return publicationList;
    }
}

