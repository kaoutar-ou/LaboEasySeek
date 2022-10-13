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
@Named("reportBean")
@RequestScoped
public class Report {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;

    @BsonProperty(value = "title")
    private String title;

    @BsonProperty(value = "version")
    private String version;

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

    public void setVersion(String version) {
        if (!Objects.equals(version, "")) {
        this.version = version;
        }
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
