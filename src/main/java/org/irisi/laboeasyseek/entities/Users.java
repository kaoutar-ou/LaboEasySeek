package org.irisi.laboeasyseek.entities;

import jakarta.xml.bind.annotation.*;

import java.util.List;

//@XmlSeeAlso({Users.class})
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Users {
    @XmlElement(name = "user")
    private List<User> users;

    public Users(List<User> users) {
        this.users = users;
    }

    public Users() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
