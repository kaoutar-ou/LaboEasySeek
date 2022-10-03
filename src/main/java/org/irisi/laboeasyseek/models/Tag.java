package org.irisi.laboeasyseek.models;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Objects;

@Getter
@ToString
@Named("tagBean")
@RequestScoped
public class Tag implements Serializable {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;
    @BsonProperty(value = "name")
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        if (!Objects.equals(name, "")) {
            this.name = name;
        }
    }
}
