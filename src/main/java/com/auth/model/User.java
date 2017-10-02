package com.auth.model;

import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "userauth15")
public class User {

    private Long id;
    private String username;
    private String password;
    private String passwordconfirm;
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String password, String passwordconfirm, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.passwordconfirm = passwordconfirm;
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // tai reiskia, kad neis i lentele, taciau turesim si,kaip objekta
    @Transient
    public String getPasswordconfirm() {
        return passwordconfirm;
    }

    public void setPasswordconfirm(String passwordconfirm) {
        this.passwordconfirm = passwordconfirm;
    }

    @ManyToMany
    @JoinTable(name = "userauth15_roleauth15", joinColumns = @JoinColumn(name = "userauth15_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roleauth15_id", referencedColumnName = "id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
