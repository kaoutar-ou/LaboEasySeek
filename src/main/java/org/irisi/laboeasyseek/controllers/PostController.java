package org.irisi.laboeasyseek.controllers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Sorts;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.irisi.laboeasyseek.models.Post;
import org.irisi.laboeasyseek.services.IPostService;

import jakarta.ejb.EJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.regex;

@SessionScoped
@Named("postController")
public class PostController implements Serializable {
    @EJB
    private IPostService postService;

    public PostController(String[] args) {
    }

    public PostController() {
    }

    private String postCategory;
    public String getPostCategory() {
        return postCategory;
    }
    public void setPostCategory(String postCategory) {
        this.postCategory = postCategory;
    }

//    private List<String> tags = new ArrayList<>();
//
//
//    public List<String> getTags() {
//        if (tags.size() == 0) {
//            tags.add("");
//        }
//        return tags;
//    }
//
//    public void setTags(List<String> tags) {
//        this.tags = tags;
//    }
//
//    public void addTag(String tag) {
//        if (!Objects.equals(tag, "")) {
//            String newTag = tag;
//            tags.add(newTag);
//        }
//    }



    public String addPost(Post post) {
        System.out.println("addPost");
        System.out.println(post);
        System.out.println(postCategory);
//        System.out.println(tags);
        if (postService.addPost(post, postCategory)) {
            System.out.println("addPost success");
            setPostCategory("");
            return "home";
        }
        return "addPost";
    }










    private String search = "";

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void searchByString(String searchString) {
        setSearch(searchString);
        setSearchTag("");
        setSearchCategory("");
//        setPageIndex(0);
    }


    private String searchTag = "";

    public String getSearchTag() {
        return searchTag;
    }

    public void setSearchTag(String searchTag) {
        this.searchTag = searchTag;
    }

    public void searchByTag(String searchTag) {
        System.out.println("search by tag---" + searchTag);
        setSearchTag(searchTag);
        setSearch("");
        setSearchCategory("");
//        setPageIndex(0);
    }

    private String searchCategory = "";

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    public void searchByCategory(String searchCategory) {
        System.out.println("search by tag---" + searchCategory);
        setSearchCategory(searchCategory);
        setSearch("");
        setSearchTag("");
//        setPageIndex(0);
    }




    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        posts = postService.getAllPosts(search, searchCategory, searchTag);
        return posts;
    }


}
