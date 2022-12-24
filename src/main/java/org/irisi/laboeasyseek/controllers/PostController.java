package org.irisi.laboeasyseek.controllers;


import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.irisi.laboeasyseek.beans.*;
import org.irisi.laboeasyseek.models.Comment;
import org.irisi.laboeasyseek.models.Keyword;
import org.irisi.laboeasyseek.models.Post;
//import org.irisi.laboeasyseek.models.Tag;
import org.irisi.laboeasyseek.models.Topic;
import org.irisi.laboeasyseek.services.IPostService;

import jakarta.ejb.EJB;
import org.irisi.laboeasyseek.utils.SessionUtils;
import org.irisi.laboeasyseek.utils.UploadHelper;
import org.primefaces.model.charts.pie.PieChartModel;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;


@SessionScoped
@Named("postController")
public class PostController implements Serializable {
    @EJB
    private IPostService postService;

    public PostController(String[] args) {
    }

    public PostController() {
    }


    private String postCategory = "";

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


    public String addPost(PostBean postBean, CalendarView calendarView, ImageBean imageBean, DocumentBean documentBean, ItemsBean itemsBean) throws IOException {
//        System.out.println("addPost");
//        System.out.println(post);
//        System.out.println(postCategory);
//        if (postService.addPost(post, postCategory)) {
//            System.out.println("addPost success");
//            setPostCategory("");
//            return "home";
//        }
//        return "addPost";

        System.out.println("addPost");
        System.out.println(postCategory);
        System.out.println(postBean);
        if (calendarView.getDate10() != null) {
            System.out.println(calendarView.getDate10());
        }
        if (calendarView.getDate14() != null) {
            System.out.println(calendarView.getDate14());
        }
        if (itemsBean.getItems() != null && itemsBean.getItems().size() > 0) {
            System.out.println(itemsBean.getItems());
        }
        if (imageBean.getPart() != null) {
            System.out.println(imageBean.getPart());
        }
        if (documentBean.getPart() != null) {
            System.out.println(documentBean.getPart());
        }

        UploadHelper uploadHelper = new UploadHelper();
        String imageTitle = null;
        String imageType = null;
        System.out.println(imageTitle);

        String documentTitle = uploadHelper.processUpload(documentBean.getPart(), "");
        String documentType = null;
        System.out.println(documentTitle);

        List<String> listWords = new ArrayList<>();

        if (imageBean.getPart() != null) {
            imageTitle = uploadHelper.processUpload(imageBean.getPart(), "");
            if (imageTitle != null) {
                imageType = imageBean.getPart().getContentType();
            }
        }

        if (documentBean.getPart() != null) {
            documentTitle = uploadHelper.processUpload(documentBean.getPart(), "");
            if (documentTitle != null) {
                documentType = documentBean.getPart().getContentType();
                if (!Objects.equals(documentType, "application/pdf")) {
                    listWords = UploadHelper.generateWordCloud(documentTitle);
                } else {
                    String file = UploadHelper.pdfToTextFile(documentTitle);
                    listWords = UploadHelper.generateWordCloud(file);
                }
            }
        }


        if(postService.addPost(postCategory, postBean, calendarView, imageTitle, imageType, documentTitle, documentType, listWords, itemsBean)) {
            System.out.println("addPost success");
            setPostCategory("");
            return "home.xhtml?faces-redirect=true";
        }
        return "addPost.xhtml?faces-redirect=true";
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
        System.out.println("posts size: " + posts.size());
//        for ( Post post : posts) {
//            for ( Tag tag : post.getTags()) {
//
//        System.out.println("posts: " + tag.getName());
//
//            }
//
//        }
        return posts;
    }

    Post post;
//    List<Tag> tags;
    List<Keyword> keywords = new ArrayList<>();

    List<Comment> comments = new ArrayList<>();


    public void handleSetPostInfo(Post post) {
//        System.out.println("setPostInfo");
////        System.out.println(post);
//        System.out.println("setPostInfo" + post.getId());
////        System.out.println(post.getTitle());
////        this.post = post;
////        this.tags = (List<Tag>) post.getTags();
////        this.keywords = (List<Keyword>) post.getDocument().getKeywords();
////        this.comments = post.getComments();
////
////        Long postId = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
////                .get("postId"));
////
////        System.out.println("postId" + postId);
//
//        this.post = postService.getPost(post.getId());
//
//        this.comments.addAll(postService.getPostComments(post.getId()));
//
//        this.tags.addAll(postService.getPostTags(post.getId()));
//
//        this.keywords.addAll(postService.getPostKeywords(post.getId()));
//
//        for (Keyword keyword: keywords) {
//            System.out.println(keyword.getName());
//        }
    }

    public Post getPost() {
//        Post post = postService.getPost(postId);
//        if (this.post == null || ( post != null && post.getId() != this.post.getId() )) {
//            setPost(post);
////            List<Tag> tags = postService.getPostTags(postId);
////            setTags(tags);
//            List<Keyword> keywords = postService.getPostKeywords(postId);
//            setKeywords(keywords);
////            System.out.println("tags size 1 : " + tags.size());
////            System.out.println("tags 2: " + tags);
//            List<Comment> comments = postService.getPostComments(postId);
//            System.out.println("comments size 1 : " + comments.size());
//            System.out.println("comments 2: " + comments);
//            setComments(comments);
//        }

        return this.post;
    }
    public void setPost(Post post) {
        this.post = post;
    }

//    public List<Tag> getTags() {
//        return tags;
//    }

    List<String> words = new ArrayList<>();

    public void setWords(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        System.out.println("tags 3: " + keywords);
        List<String> words = new ArrayList<>();
        for (Keyword keyWord : keywords) {
            words.add(keyWord.getName());
        }
        System.out.println("tagNames : " + words);
        return words;
    }

//    public void setTags(List<Tag> tags) {
//        this.tags = tags;
//    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public List<Comment> getComments() {
        List<Comment> commentList = postService.getPostComments(postId);
        setComments(commentList);
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    List<Topic> firstTopic = new ArrayList<>();
    List<Topic> secondTopic = new ArrayList<>();
    List<Topic> thirdTopic = new ArrayList<>();

    public List<Topic> getFirstTopic() {
        return firstTopic;
    }

    public void setFirstTopic(List<Topic> firstTopic) {
        this.firstTopic = firstTopic;
    }

    public List<Topic> getSecondTopic() {
        return secondTopic;
    }

    public void setSecondTopic(List<Topic> secondTopic) {
        this.secondTopic = secondTopic;
    }

    public List<Topic> getThirdTopic() {
        return thirdTopic;
    }

    public void setThirdTopic(List<Topic> thirdTopic) {
        this.thirdTopic = thirdTopic;
    }

    PieChartModel pieChartModel;

    public PieChartModel getPieChartModel() {
        return pieChartModel;
    }

    public void setPieChartModel(PieChartModel pieChartModel) {
        this.pieChartModel = pieChartModel;
    }

    Long postId;
    public String handleSetPost(Long id) {

        this.postId = id;
        Post post = postService.getPost(postId);
//        if (this.post == null || ( post != null && post.getId() != this.post.getId() )) {
            setPost(post);
//            List<Tag> tags = postService.getPostTags(postId);
//            setTags(tags);
            List<Keyword> keywords = postService.getPostKeywords(postId);
            setKeywords(keywords);
//            System.out.println("tags size 1 : " + tags.size());
//            System.out.println("tags 2: " + tags);
            List<Comment> comments = postService.getPostComments(postId);
            System.out.println("comments size 1 : " + comments.size());
            System.out.println("comments 2: " + comments);
            setComments(comments);

            System.out.println("documentId: " + post.getDocument().getId());
            List<Topic> topics = postService.getTopicsByDocumentId(post.getDocument().getId());

            List<Topic> topic1 = new ArrayList<>();
            List<Topic> topic2 = new ArrayList<>();
            List<Topic> topic3 = new ArrayList<>();

            for (Topic topic : topics) {
                topic.setWeight((double) Math.round(topic.getWeight() * 100) / 100);
                if (topic.getNumber() == 1) {
                    topic1.add(topic);
                } else if (topic.getNumber() == 2) {
                    topic2.add(topic);
                } else if (topic.getNumber() == 3) {
                    topic3.add(topic);
                }
            }

            setFirstTopic(topic1);
            setSecondTopic(topic2);
            setThirdTopic(topic3);

            ChartJsView chartJsView = new ChartJsView();

            PieChartModel pieChartModel1 = new PieChartModel();

            pieChartModel1 = chartJsView.getCustomPieModel(comments);

            setPieChartModel(pieChartModel1);

//        }

        return "post.xhtml";

//        if (id != postId) {
//            this.postId = id;
//        }
//        return "post.xhtml";
    }

    public String addComment(CommentBean commentBean) {
        System.out.println("addComment");
        System.out.println("postId: " + postId);
        System.out.println("comment: " + commentBean.getContent());
        Long userId = Long.parseLong(Objects.requireNonNull(SessionUtils.getUserId()));
        if (postService.addComment(commentBean, postId, userId)) {
            return "post.xhtml?faces-redirect=true";
        }
        return "post.xhtml?faces-redirect=true";
    }

}
