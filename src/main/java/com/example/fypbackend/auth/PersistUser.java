package com.example.fypbackend.auth;

import com.example.fypbackend.comment.Comment;
import com.example.fypbackend.comment.CommentRecipient;
import com.example.fypbackend.posts.Post;
import com.example.fypbackend.user.Groups;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class PersistUser {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    @Column(name="USERNAME")
    private String username;
    @Column(name="PASSWORD")
    private String password;
    @Column(name="ROLE")
    private String role;
    @Column(name="account_non_expired")
    private int accountNonExpired;
    @Column(name="account_non_locked")
    private int accountNonLocked;
    @Column(name="credentials_non_expired")
    private int credentialsNonExpired;
    @Column(name="enabled")
    private int enabled;
    @Column(name="loginattempts", nullable = true)
    private Integer loginattempts;

    //CommentRecipient

    @OneToOne(mappedBy = "recipientUser", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private CommentRecipient commentRecipient;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_groups",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id",
                            nullable = false, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(name = "groups_id", referencedColumnName = "id",
                            nullable = false, updatable = true)})
    private Set<Groups> groups = new HashSet<>();

    public PersistUser() {}

    public PersistUser(String username, String password, String role, int accountNonExpired, int accountNonLocked, int credentialsNonExpired, int enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getAccountNonExpired() {
        return accountNonExpired == 1;
    }

    public void setAccountNonExpired(int accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean getAccountNonLocked() {
        return accountNonLocked == 1;
    }

    public void setAccountNonLocked(int accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean getCredentialsNonExpired() {
        return credentialsNonExpired == 1;
    }

    public void setCredentialsNonExpired(int credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean getEnabled() {
        return enabled == 1;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Integer getLoginattempts() {
        return loginattempts;
    }

    public void setLoginattempts(Integer loginattempts) {
        this.loginattempts = loginattempts;
    }

    @Override
    public String toString() {
        return "PersistUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
//                ", posts=" + posts +
                '}';
    }
}
