package org.irisi.laboeasyseek.entities;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "userr")
//@Table(name = "post")
@Named("userrBean")
@RequestScoped
public class Userr implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}