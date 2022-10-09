package org.irisi.laboeasyseek.models;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.ToString;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

@Getter
@ToString
@Named("articleBean")
@RequestScoped
public class Article {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;

    @BsonProperty(value = "title")
    private String title;

    @BsonProperty(value = "content")
    private String content;

    @BsonProperty(value = "reference")
    private String reference;

    @BsonProperty(value = "media")
    private Media media;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
