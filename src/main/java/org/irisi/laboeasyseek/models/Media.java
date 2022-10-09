package org.irisi.laboeasyseek.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;

@Getter
@Setter
@ToString
public class Media {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;
    @BsonProperty(value = "title")
    private String title;
    @BsonIgnore
    private String type;

    public void setType(String title) {
        if (title.length() > 0) {
            String[] parts = title.split("\\.");
            if (parts.length > 0) {
                this.type = parts[parts.length - 1];
            }
        }
    }

    private Image image;
    private Document document;
}
