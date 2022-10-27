package org.irisi.laboeasyseek.models;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@ToString
@RequestScoped
@Named("comment")
public class Comment {
    @BsonId()
//    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;
    @BsonProperty(value = "username")
    private String username;
    @BsonProperty(value = "content")
    private String content;
    @BsonProperty(value = "created_at")
    private String createdAt;
}