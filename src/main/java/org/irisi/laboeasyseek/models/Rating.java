package org.irisi.laboeasyseek.models;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.ToString;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import java.io.Serializable;
import java.util.Objects;

@Getter
@ToString
@RequestScoped
@Named( "ratingBean" )
public class Rating implements Serializable {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;

    @BsonProperty(value = "user")
    private String user;

    @BsonProperty(value = "rating")
    private int rating;

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(String user) {
        if (!Objects.equals(user, "")) {
            this.user = user;
        }
    }

    public void setRating(int rating) {
        if (!Objects.equals(rating, "")) {
            this.rating = rating;
        }
    }
}
