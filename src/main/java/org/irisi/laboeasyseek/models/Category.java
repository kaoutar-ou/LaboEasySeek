package org.irisi.laboeasyseek.models;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "category")
@Entity
public class Category {
    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>( );

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;



    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}