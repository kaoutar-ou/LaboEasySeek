package org.irisi.laboeasyseek.models;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequestScoped
@Named("document")
@Table(name = "document")
@Entity
@Getter
@Setter
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "type", nullable = true)
    private String type;

    @Column(name = "file_path", nullable = true)
    private String filePath;

    @OneToOne(mappedBy = "document", orphanRemoval = true)
    private Post post;

    @ManyToMany(targetEntity = Keyword.class)
    @JoinTable(name = "document_keyword",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private List<Keyword> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "document", orphanRemoval = true)
    List<Topic> topics = new ArrayList<>();

}