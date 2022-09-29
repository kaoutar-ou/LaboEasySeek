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

@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "document")
@XmlType(propOrder = { "id", "title" })
@Named("documentBean")
@SessionScoped
public class Document implements Serializable {
    private Long id;

    private String title;

    public String getTitle() {
        return title;
    }

    @XmlElement(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    @XmlAttribute
    public void setId(Long id) {
        this.id = id;
    }
}
