package org.irisi.laboeasyseek.models;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import java.io.Serializable;
import java.util.Objects;

@Getter
@ToString
@SessionScoped
@Named( "mediaImageBean" )
public class Image implements Serializable {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;
    @BsonProperty(value = "title")
    private String title;

    @BsonIgnore
    private Part part;

    @BsonProperty(value = "type")
    private String type;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        if (!Objects.equals(title, "")) {
        this.title = title;
        }
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setType(String type) {
        if (!Objects.equals(type, "")) {
        this.type = type;
        }
    }
}