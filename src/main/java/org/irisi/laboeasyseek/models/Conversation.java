package org.irisi.laboeasyseek.models;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Named("conversation")
@Table(name = "conversation")
@Entity
@Getter
@Setter

public class Conversation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  //One to many with messages
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "conversation")
    private List<Message> messages = new ArrayList<>( );

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    //Many to many with users
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "conversation_user", joinColumns = @JoinColumn(name = "conversation_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<User> users = new ArrayList<>( );
//
//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }
//


}