package org.irisi.laboeasyseek.models;

import javax.persistence.*;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.*;

@ViewScoped
@Named("post")
@Table(name = "post")
@Entity
@Getter
@Setter
public class Post implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>( );

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "time", nullable = true)
    @Temporal(TemporalType.TIME)
    private Date time;

    @Column(name = "location", nullable = true)
    private String location;

    @Column(name = "content", nullable = true, length = 2500)
    private String content;

    @Column(name = "version", nullable = true)
    private String version;

    @Column(name = "views_number", nullable = false)
    private int viewNumber = 0;

    @Column(name = "likes_number", nullable = false)
    private int likesNumber = 0;

    @Column(name = "dislikes_number", nullable = false)
    private int dislikesNumber = 0;

    // language website ref

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "document_id")
    private Document document;

//    @ManyToMany(targetEntity = Tag.class)
//    @JoinTable(name = "post_tag",
//            joinColumns = @JoinColumn(name = "post_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id"))
//    private List<Tag> tags = new ArrayList<>();

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}