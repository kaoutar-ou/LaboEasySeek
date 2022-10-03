package org.irisi.laboeasyseek.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;

import java.util.Collection;

@Getter
@Setter
@ToString
public class User {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;
//    @BsonProperty(value = "username")
//    private String username;
    @BsonProperty(value = "user_id")
    private Long userId;
    @BsonProperty(value = "email")
    private String email;
    private Collection<Publication> publications;
}
