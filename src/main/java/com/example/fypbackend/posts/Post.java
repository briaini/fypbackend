package com.example.fypbackend.posts;

import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.comment.Comment;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    String media;
    String category;
    String title;
    String description;
    String link_url;
    String image_url;

    @ManyToMany(mappedBy = "posts", fetch = FetchType.LAZY)
    private Set<PersistUser> users = new HashSet<>();

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinTable(name = "posts_comments",
//            joinColumns = {
//                    @JoinColumn(name = "post_id", referencedColumnName = "id",
//                            nullable = false, updatable = false)},
//            inverseJoinColumns = {
//                    @JoinColumn(name = "comment_id",
//                            nullable = false, updatable = false)})
//    private Set<Comment> comments = new HashSet<>();

    Post(String title, String description, String media, String category, String link_url, String image_url) {
        this.title = title;
        this.description = description;
        this.media = media;
        this.category = category;
        this.link_url = link_url;
        this.image_url = image_url;
    }


    public Post() {}



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkUrl() {
        return link_url;
    }

    public void setLinkUrl(String linkUrl) {
        this.link_url = linkUrl;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String imageUrl) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", media='" + media + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link_url='" + link_url + '\'' +
                ", image_url='" + image_url + '\'' +
//                ", users=" + users +
                '}';
    }
}
