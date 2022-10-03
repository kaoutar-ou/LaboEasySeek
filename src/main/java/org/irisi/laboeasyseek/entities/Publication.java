package org.irisi.laboeasyseek.entities;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//@Entity
//@Table(name = "publication")
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "publication")
@XmlType(propOrder = { "id", "title", "description", "media", "documents" })
//@Named("publicationBean")
//@SessionScoped
public class Publication implements Serializable {

    private Long id;

    private String title;

    private String description;


    private MediaTest mediaTest;

    private Documents documents;

    public MediaTest getMediaTest() {
        return mediaTest;
    }

    @XmlElement(name = "media")
    public void setMediaTest(MediaTest mediaTest) {
        this.mediaTest = mediaTest;
    }

    public Documents getDocuments() {
        return documents;
    }

    @XmlElement(name = "documents")
    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement(name = "description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    @XmlAttribute
    public void setId(Long id) {
        this.id = id;
    }

}