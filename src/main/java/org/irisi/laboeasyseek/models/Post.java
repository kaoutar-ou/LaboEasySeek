package org.irisi.laboeasyseek.models;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
//import org.bson.codecs.pojo.annotations.BsonRepresentation;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequestScoped
@Named("post")
public class Post {
    @BsonProperty("_id")
    @BsonId()
//    @BsonRepresentation(BsonType.OBJECT_ID)
    private ObjectId id;
    @BsonProperty(value = "title")
    private String title;
    @BsonProperty(value = "description")
    private String description;
    @BsonProperty(value = "category")
    private String category;
    @BsonProperty(value = "language")
    private String language;
    @BsonProperty(value = "eventName")
    private String eventName;
    @BsonProperty(value = "eventDate")
    private String eventDate;
    @BsonProperty(value = "eventLocal")
    private String eventLocal;
    @BsonProperty(value = "articleTitle")
    private String articleTitle;
    @BsonProperty(value = "articleContent")
    private String articleContent;
    @BsonProperty(value = "reference")
    private String reference;
    @BsonProperty(value = "reportTitle")
    private String reportTitle;
    @BsonProperty(value = "version")
    private String version;
    @BsonProperty(value = "website")
    private String website;
    @BsonProperty(value = "tags")
    private List<String> tags;
    @BsonProperty(value = "publisher")
    private String publisher;
    @BsonProperty(value = "views_number")
    private int viewsNumber;
    @BsonProperty(value = "likes_number")
    private int likesNumber;
    @BsonProperty(value = "comments")
    private List<Comment> comments;

    public void postCategory(String category) {
        this.category = category;
    }

//    public void addTag(String tag) {
//        if (this.tags == null) {
//            this.tags = new ArrayList<>();
//        }
//        this.tags.add(tag);
//    }
}
