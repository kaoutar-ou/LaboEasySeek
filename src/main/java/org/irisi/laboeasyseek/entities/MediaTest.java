package org.irisi.laboeasyseek.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "media")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaTest {
    @XmlElement(name = "photo")
    private List<Photo> photos;

    public MediaTest(List<Photo> photos) {
        this.photos = photos;
    }

    public MediaTest() {
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
