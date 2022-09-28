//package org.irisi.laboeasyseek.entities;
//
//import jakarta.enterprise.context.SessionScoped;
//import jakarta.inject.Named;
//import javax.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//import java.io.Serializable;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "user")
//@Named("userBean")
//@SessionScoped
//public class User implements Serializable {
//
//    private static final long serialVersionUID = -5435850275907435915L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "password")
//    private String password;
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//}
