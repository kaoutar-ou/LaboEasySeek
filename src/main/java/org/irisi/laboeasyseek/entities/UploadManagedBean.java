package org.irisi.laboeasyseek.entities;


import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

import java.io.Serializable;

@SessionScoped
@Named( "uploadManagedBean")
public class UploadManagedBean implements Serializable {

    private Part part;

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }



}