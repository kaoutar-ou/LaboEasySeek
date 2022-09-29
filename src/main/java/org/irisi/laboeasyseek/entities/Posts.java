package org.irisi.laboeasyseek.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Posts {

    @XmlElement(name = "post")
    private List<Post> posts = new ArrayList<>();

    public Posts(List<Post> posts) {
        this.posts = posts;
    }

    public Posts() {
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
