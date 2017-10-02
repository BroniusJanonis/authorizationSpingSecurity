package com.auth.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roleauth15")
public class Role {

    private Long id;
    private String name;
    private Set<User> users;

    public Role() {
    }

    public Role(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setname(String role) {
        this.name = role;
    }

    // mappedby parasom, pagal koki objekta mapinam
    @ManyToMany (mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
