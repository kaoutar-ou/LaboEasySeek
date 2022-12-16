package org.irisi.laboeasyseek.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ViewScoped
@Named("commentBean")
@Getter
@Setter
@ToString
public class CommentBean implements Serializable {
    private String content;
}
