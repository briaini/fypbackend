package com.example.fypbackend.comment;

import com.example.fypbackend.posts.Post;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    int id;
    int userId;
    String textBody;
    int postId;
    int parentCommentId;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<CommentRecipient> recipient;

//    Date date;





//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "post", nullable = false)
//    private Post post;

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

    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    //    public Post getPost() {
//        return post;
//    }
//
//    public void setPost(Post post) {
//        this.post = post;
//    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", postId=" + postId +
                ", textBody='" + textBody + '\'' +
                '}';
    }
}
