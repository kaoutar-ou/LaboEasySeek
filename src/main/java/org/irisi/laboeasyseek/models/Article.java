package org.irisi.laboeasyseek.models;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.ToString;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import java.util.Objects;

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

    @BsonProperty(value = "language")
    private String language;

    @BsonProperty(value = "media")
    private Media media;

    public void setLanguage(String language) {
        if (!Objects.equals(language, "")) {
            this.language = language;
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        if (!Objects.equals(title, "")) {
            this.title = title;
        }
    }

    public void setReference(String reference) {
        if (!Objects.equals(reference, "")) {
        this.reference = reference;
        }
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public void setContent(String content) {
        if (!Objects.equals(content, "")) {
        this.content = content;
        }
    }
}
