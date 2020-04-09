package com.example.fypbackend.user;

import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.posts.Post;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Groups {
    @Id
    @GeneratedValue
    Integer id;
    String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "groups_posts",
            joinColumns = {
                    @JoinColumn(name = "groups_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "post_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Post> posts = new HashSet<>();


    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<PersistUser> members = new HashSet<>();

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

    public Set<PersistUser> getMembers() {
        return members;
    }

    public void setMembers(Set<PersistUser> members) {
        this.members = members;
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