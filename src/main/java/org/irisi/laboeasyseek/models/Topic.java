package org.irisi.laboeasyseek.models;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


@ViewScoped
@Named("topic")
@Table(name = "topic")
@Entity
@Getter
@Setter
@ToString
public class Topic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "weight", nullable = false)
    private double weight;

    //Many to one to document
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;


}