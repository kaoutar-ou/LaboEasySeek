package org.irisi.laboeasyseek.models;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;
import org.irisi.laboeasyseek.utils.SessionUtils;

import java.io.Serializable;
import java.util.*;

@Getter
@ToString
@Named("publicationBean")
@RequestScoped
public class Publication implements Serializable {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
//    @BsonProperty("id")
    private String id;
    @BsonProperty(value = "title")
    private String title;
    @BsonProperty(value = "description")
    private String description;

    @BsonProperty(value = "publisher")
    private String publisher = SessionUtils.getEmail();


    @BsonProperty(value = "views_number")
    private int viewsNumber;

    @BsonProperty(value = "category")
    private String category;

    @BsonProperty(value = "report")
    private Report report;

    @BsonProperty(value = "article")
    private Article article;

    @BsonProperty(value = "created_at")
    private Date createdAt;

    @BsonProperty(value = "media")
    private Media media;

    @BsonProperty(value = "event")
    private Event event;

    @BsonProperty(value = "website")
    private String website;


    private List<Tag> tags;

    private List<Comment> comments;

    private List<Rating> ratings;

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void setWebsite(String website) {

        if (!Objects.equals(website, "")) {
        this.website = website;
        }
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public void setCategory(String category) {
        if (!Objects.equals(category, "")) {
        this.category = category;
        }
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        if (!Objects.equals(title, "")) {
            this.title = title;
        }
    }

    public void setDescription(String description) {
        if (!Objects.equals(description, "")) {
            this.description = description;
        }
    }

    public void setPublisher(String publisher) {
        if (!Objects.equals(publisher, "")) {
        this.publisher = publisher;
        }
    }

    public void setViewsNumber(int viewsNumber) {
        this.viewsNumber = viewsNumber;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}