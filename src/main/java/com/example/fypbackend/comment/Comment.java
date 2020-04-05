package com.example.fypbackend.comment;

import com.example.fypbackend.posts.Post;

import javax.persistence.*;


@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    int id;
    int userId;
    String textBody;
//    Date date;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "post", nullable = false)
    private Post post;

    public Comment() {}

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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", textBody='" + textBody + '\'' +
                '}';
    }
}
