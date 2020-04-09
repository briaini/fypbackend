package com.example.fypbackend.comment;

import javax.persistence.*;

@Entity
@Table(name = "commentrecipient")
public class CommentRecipient {
    @Id
    @GeneratedValue
    int id;
    int recipientId;
    int recipientGroupId;
//    int commentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;


    public CommentRecipient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getRecipientGroupId() {
        return recipientGroupId;
    }

    public void setRecipientGroupId(int recipientGroupId) {
        this.recipientGroupId = recipientGroupId;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    //    public int getCommentId() {
//        return commentId;
//    }
//
//    public void setCommentId(int commentId) {
//        this.commentId = commentId;
//    }
}
