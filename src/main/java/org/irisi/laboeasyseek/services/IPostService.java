package org.irisi.laboeasyseek.services;

import jakarta.ejb.Remote;
import org.irisi.laboeasyseek.beans.CalendarView;
import org.irisi.laboeasyseek.beans.CommentBean;
import org.irisi.laboeasyseek.beans.ItemsBean;
import org.irisi.laboeasyseek.beans.PostBean;
import org.irisi.laboeasyseek.models.Comment;
import org.irisi.laboeasyseek.models.Keyword;
import org.irisi.laboeasyseek.models.Post;
//import org.irisi.laboeasyseek.models.Tag;

import java.util.List;

@Remote
public interface IPostService {

//    public Boolean addPost(Post post, String category);
//    public Boolean addPost(String postCategory, PostBean postBean, CalendarView calendarView, ImageBean imageBean, DocumentBean documentBean, ItemsBean itemsBean);
    public Boolean addPost(String postCategory, PostBean postBean, CalendarView calendarView, String imageTitle, String imageType, String documentTitle, String documentType, List<String> listWords, ItemsBean itemsBean);
    public String deletePost();
    public String updatePost();
    public Post getPost(Long id);
    public List<Post> getAllPosts(String search, String searchCategory, String searchTag);

    public Boolean addComment(CommentBean comment, Long postId, Long userId);
    List<Comment> getPostComments(Long id);
//    List<Tag> getPostTags(Long id);
    List<Keyword> getPostKeywords(Long id);
//    String getPostCategory();
//    void setPostCategory(String postCategory);
}
