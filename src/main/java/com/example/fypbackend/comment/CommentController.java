package com.example.fypbackend.comment;

import com.example.fypbackend.posts.Post;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@RestController
@RequestMapping("")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping(path = "/{userId}/comments") // Map ONLY POST Requests
    public @ResponseBody
    String createPost(@RequestBody String body, @PathVariable("userId") Integer userId) {
        System.out.println("test before Gson:\n " + body + "\n");
        Gson gson = new Gson();
        Comment comment = gson.fromJson(body, Comment.class);
        comment.setUserId(userId);
        commentRepository.save(comment);
        System.out.println("Comment created: " + comment.toString());
        return "TODO";
    }


    @GetMapping(path = "/comments")
    public Iterable<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "users/{userId}/comments")
    public Iterable<Comment> getAllComments(@PathVariable int userId) {
        System.out.println(userId);
        return commentRepository.findAll();
    }


    @GetMapping(path = "/comments/{commentId}")
    public Comment getComment(@PathVariable("commentId") Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("Comment (" + commentId + ") doesn't exist"));
//                POSTS.stream()
//                .filter(post -> postId.equals(post.getId()))
//                .findFirst()
//                .orElseThrow(() ->new IllegalStateException("student " + postId + "doesn't exist"));
    }
}