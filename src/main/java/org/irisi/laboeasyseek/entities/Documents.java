package org.irisi.laboeasyseek.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "documents")
@XmlAccessorType(XmlAccessType.FIELD)
public class Documents {

    @XmlElement(name = "document")
    private List<Document> documents;

    public Documents(List<Document> documents) {
        this.documents = documents;
    }

    public Documents() {
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
