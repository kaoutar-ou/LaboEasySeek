package org.irisi.laboeasyseek.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "publications")
@XmlAccessorType(XmlAccessType.FIELD)
public class Publications {
    @XmlElement(name = "publication")
    private List<Publication> publications = new ArrayList<>();

    public Publications(List<Publication> publications) {
        this.publications = publications;
    }

    public Publications() {
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }
}
