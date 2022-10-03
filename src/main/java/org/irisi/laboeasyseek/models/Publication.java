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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
@Named("publicationBean")
@RequestScoped
public class Publication implements Serializable {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;
    @BsonProperty(value = "title")
    private String title;
    @BsonProperty(value = "description")
    private String description;

    @BsonProperty(value = "publisher")
    private String publisher = SessionUtils.getEmail();


    @BsonProperty(value = "views_number")
    private int viewsNumber = 0;

    private Media media;
    private Event event;
//    private Tag tag;

    private List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

//    private List<Tag> tags = new ArrayList<>();
//
//    public List<Tag> getTags() {
//        if(tags.size()==0) {
//            tags.add(new Tag()); // adding the first item
//        }
//        return tags;
////        return tags;
//    }
//
//    public void setTags(List<Tag> tags) {
//        this.tags = tags;
//    }

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
        this.publisher = publisher;
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

//    public void setTag(Tag tag) {
//        this.tag = tag;
//    }
}
