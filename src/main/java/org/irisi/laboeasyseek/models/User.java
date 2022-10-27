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

import java.util.Collection;

@Getter
@Setter
@ToString
@RequestScoped
@Named("user")
public class User {
    @BsonProperty("_id")
    @BsonId()
//    @BsonRepresentation(BsonType.OBJECT_ID)
    private ObjectId id;
    @BsonProperty(value = "username")
    private String username;
    @BsonProperty(value = "email")
    private String email;
    @BsonProperty(value = "password")
    private String password;
}
