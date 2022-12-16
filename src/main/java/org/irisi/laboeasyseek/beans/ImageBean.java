package org.irisi.laboeasyseek.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("imageBean")
@Getter
@Setter
@ToString
public class ImageBean implements Serializable {
    private String title;
    private Part part;
    private String filePath;
    private List<String> keywords;
}
