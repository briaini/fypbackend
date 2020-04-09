package com.example.fypbackend.comment;

import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.user.Groups;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "commentrecipient")
public class CommentRecipient {
    @Id
    @GeneratedValue
    int id;
//    int recipientId;
//    int recipientGroupId;
//    int commentId;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "comment_singlerecipient", nullable = true)
    private PersistUser recipientUser;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "comment_grouprecipient", nullable = true)
    private Groups recipientGroup;

    //think should be one to many as below
//    @OneToOne(mappedBy = "recipient", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private Comment comment;

    @OneToMany(mappedBy = "commentRecipient", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Comment> comments;

    public CommentRecipient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersistUser getRecipientUser() {
        return recipientUser;
    }

    public void setRecipientUser(PersistUser recipientUser) {
        this.recipientUser = recipientUser;
    }

    public Groups getRecipientGroup() {
        return recipientGroup;
    }

    public void setRecipientGroup(Groups recipientGroup) {
        this.recipientGroup = recipientGroup;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
