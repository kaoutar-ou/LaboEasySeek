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
@XmlType(propOrder = {"id","title","photo","description"})
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