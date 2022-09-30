package org.irisi.laboeasyseek.entities;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "event")
@XmlType(propOrder = { "id", "eventName", "eventDate", "eventLocal" })
@Named("eventBean")
@SessionScoped
public class Event implements Serializable {

    private String eventName;

    private String eventDate;

    private String eventLocal;

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

}
