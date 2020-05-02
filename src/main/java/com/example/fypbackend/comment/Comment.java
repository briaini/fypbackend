package com.example.fypbackend.comment;

import com.example.fypbackend.posts.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    int id;
    int subjectId;
    String textBody;
    Integer postId = null;
    int parentCommentId;

    //should be many to one as below
//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "comment_recipient", nullable = false)
//    private CommentRecipient recipient;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_recipient", nullable = false)
    private CommentRecipient commentRecipient;


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

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public CommentRecipient getCommentRecipient() {
        return commentRecipient;
    }

    public void setCommentRecipient(CommentRecipient commentRecipient) {
        this.commentRecipient = commentRecipient;
    }
}
