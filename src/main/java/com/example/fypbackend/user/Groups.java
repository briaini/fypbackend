package com.example.fypbackend.user;

import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.comment.Comment;
import com.example.fypbackend.comment.CommentRecipient;
import com.example.fypbackend.posts.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "groups")
public class Groups {
    @Id
    @GeneratedValue
    Integer id;
    String name;
    Boolean isMdt;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "groups_posts",
            joinColumns = {
                    @JoinColumn(name = "groups_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "post_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "groups_hiddenposts",
            joinColumns = {
                    @JoinColumn(name = "groups_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "post_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Post> hiddenposts = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<PersistUser> members = new HashSet<>();

    @OneToOne(mappedBy = "recipientGroup", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private CommentRecipient recipient;

    public Groups() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Post> getHiddenposts() {
        return hiddenposts;
    }

    public void setHiddenposts(Set<Post> hiddenposts) {
        this.hiddenposts = hiddenposts;
    }

    //    @JsonIgnore
    public List<UserDAO> getMembers() {
//        return members;
        return members.stream().map(x-> new UserDAO(x.getId(), x.getUsername(), x.getRole())).collect(Collectors.toList());
    }

    public void setMembers(Set<PersistUser> members) {
        this.members = members;
    }

    public Boolean getMdt() {
        return isMdt;
    }

    public void setMdt(Boolean mdt) {
        isMdt = mdt;
    }

//    @JsonIgnore
    public CommentRecipient getRecipient() {
        return recipient;
    }

    public void setRecipient(CommentRecipient recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", posts=" + posts +
//                ", members=" + members +
                '}';
    }
}