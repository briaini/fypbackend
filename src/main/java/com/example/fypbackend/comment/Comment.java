package com.example.fypbackend.comment;

import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.posts.Post;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    int id;
    int userId;
    String textBody;
//    Date date;

//    @ManyToMany(mappedBy = "comments", fetch = FetchType.LAZY)
//    private Set<Post> posts = new HashSet<>();

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinTable(name = "posts_comments",
//            joinColumns = {
//                    @JoinColumn(name = "post_id", referencedColumnName = "id",
//                            nullable = false, updatable = false)},
//            inverseJoinColumns = {
//                    @JoinColumn(name = "comment_id",
//                            nullable = false, updatable = false)})
//    private Set<Comment> comments = new HashSet<>();

    public Comment() {}

//    public Comment(int userId, int postId, String textBody, Date date) {
//        System.out.print("creating comment with args");
//        this.userId = userId;
//        this.postId = postId;
//        this.textBody = textBody;
//        this.date = date;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", textBody='" + textBody + '\'' +
//                ", date=" + date +
                '}';
    }
}
