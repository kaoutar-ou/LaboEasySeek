package org.irisi.laboeasyseek.models;

import jakarta.enterprise.context.SessionScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import java.io.Serializable;

@Getter
@Setter
@ToString
@SessionScoped
public class Comment implements Serializable {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;

    @BsonProperty(value = "user")
    private String user;

    @BsonProperty(value = "content")
    private String content;
}
