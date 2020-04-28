package com.example.fypbackend.auth;

import com.example.fypbackend.comment.Comment;
import com.example.fypbackend.posts.Post;

import java.util.List;

public class Patient {
    final int id;
    final String name;
    final Integer mdtId;
//    final List<Integer> posts;
//    final List<Comment> comments;
//    final List<Integer> mdt;

    public Patient(int id, String name, Integer mdtId) {
//                   List<Integer> posts, List<Comment> comments, List<Integer> mdt) {
        this.id = id;
        this.name = name;
        this.mdtId = mdtId;
//        this.posts = posts;
//        this.comments = comments;
//        this.mdt = mdt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMdtId() {
        return mdtId;
    }

    //    public List<Integer> getPosts() {
//        return posts;
//    }

//    public List<Integer> getMdt() {
//        return mdt;
//    }
//
//    public List<Comment> getComments() {
//        return comments;
//    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mdtId=" + mdtId +
//                ", mdt=" + mdt +
                '}';
    }
}
