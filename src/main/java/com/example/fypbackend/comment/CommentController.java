package com.example.fypbackend.comment;

import com.example.fypbackend.posts.Post;
import com.example.fypbackend.posts.PostRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    //old way, changing comment structure
//    @PostMapping(path = "users/{userId}/posts/{postId}/comments") // Map ONLY POST Requests
//    public @ResponseBody String createComment(@RequestBody String body, @PathVariable("userId") Integer userId, @PathVariable("postId") Integer postId) {
//        System.out.println("test before Gson:\n " + body + "\n");
////        System.out.println("userId:" + userId);
////        System.out.println("postId:" + postId);
////        Post post = postRepository.findById(postId).get();
//        Gson gson = new Gson();
//        Comment comment = gson.fromJson(body, Comment.class);
//        comment.setUserId(userId);
//        comment.setPostId(postId);
////        comment.setPost(post);
//        commentRepository.save(comment);
//        return "Comment created";
//    }

    @GetMapping(path = "users/{userId}/comments")
    public List<Comment> getComments(@PathVariable int userId) {
//        public String getAllComments(@PathVariable int userId) {
//        System.out.println("getting comments");
//        System.out.println(commentRepository.findByUserId(0));
//        List<Comment> commentList = new ArrayList();
        return commentRepository.findByUserId(userId);
    }

    @GetMapping(path = "/comments")
    public Iterable<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}