package org.irisi.laboeasyseek.entities;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@XmlSeeAlso({User.class})
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "user")
@XmlType(propOrder = { "id", "email", "publications" })
@Named("userXMLBean")
@SessionScoped
public class User implements Serializable {
    private static final long serialVersionUID = -5435850275007435405L;

    private Long id;

    private String email;

    private Publications publications;


    public String getEmail() {
        return email;
    }

    @XmlElement(name = "email")
    public void setEmail(String email) {
        this.email = email;
    }

    public Publications getPublications() {
        return publications;
    }

    @XmlElement(name = "publications")
    public void setPublications(Publications publications) {
        this.publications = publications;
    }


    public Long getId() {
        return id;
    }

    @XmlAttribute()
    public void setId(Long id) {
        this.id = id;
    }
}
