package com.example.fypbackend.auth;

import com.example.fypbackend.comment.Comment;
import com.example.fypbackend.posts.Post;

import java.util.List;

public class Patient {
    final int id;
    final String name;
    final List<Integer> posts;
    final List<Comment> comments;
    final List<Integer> mdt;

    public Patient(int id, String name, List<Integer> posts, List<Comment> comments, List<Integer> mdt) {
        this.id = id;
        this.name = name;
        this.posts = posts;
        this.comments = comments;
        this.mdt = mdt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getPosts() {
        return posts;
    }

    public List<Integer> getMdt() {
        return mdt;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
