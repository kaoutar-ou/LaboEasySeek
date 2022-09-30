package org.irisi.laboeasyseek.entities;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

//@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
@XmlRootElement(name = "post")
@XmlType(propOrder = {"id","title","photo","description", "website", "category", "publisher", "eventName", "eventDate", "eventLocal", "tag", "version"})
@Named("postBean")
@SessionScoped
public class Post implements Serializable {

    private static final long serialVersionUID = -5435850275007435405L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

//    @Column(name = "name")
//    private String name;

    private String photo;

    private String title;

    private String description;

    private String website;

    private String category;

    private String publisher;

    private String eventName;

    private String eventDate;

    private String eventLocal;

    private String tag;

    private String version;

    public String getVersion() {
        return version;
    }

    @XmlElement(name = "version")
    public void setVersion(String version) {
        this.version = version;
    }

    public String getEventName() {
        return eventName;
    }

    @XmlElement(name = "eventName")
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    @XmlElement(name = "eventDate")
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocal() {
        return eventLocal;
    }

    @XmlElement(name = "eventLocal")
    public void setEventLocal(String eventLocal) {
        this.eventLocal = eventLocal;
    }

    public String getTag() {
        return tag;
    }

    @XmlElement(name = "tag")
    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getWebsite() {
        return website;
    }

    @XmlElement(name = "website")
    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCategory() {
        return category;
    }

    @XmlElement(name = "category")
    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    @XmlElement(name = "publisher")
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement(name = "description")
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

//    public Post(long id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    @XmlElement(name = "name")
//    public void setName(String name) {
//        this.name = name;
//    }

    public Long getId() {
        return id;
    }

    @XmlAttribute
    public void setId(Long id) {
        this.id = id;
    }


    public String getPhoto() {
        return photo;
    }

    @XmlElement(name = "photo")
    public void setPhoto(String photo) {
        this.photo = photo;
    }




}