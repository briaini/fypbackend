package com.example.fypbackend.posts;

import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.auth.PersistUserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PersistUserRepository userRepository;

//    Works normal createPost
    @PostMapping(path = "")
    public @ResponseBody String createPost(@RequestBody String body) {
          System.out.println("test before Gson:\n " + body + "\n");
          Gson gson = new Gson();
          Post post = gson.fromJson(body, Post.class);
        postRepository.save(post);
        return "Added Post";
    }

    @PutMapping(path = "")
    public @ResponseBody String updatePost(@RequestBody String body) {
        System.out.println("test before Gson:\n " + body + "\n");
        Gson gson = new Gson();
        Post post = gson.fromJson(body, Post.class);
        postRepository.save(post);
        return "Added Post";
    }

    @DeleteMapping(path = "/{postId}")
    public @ResponseBody String deletePost(@PathVariable("postId") Integer postId) {
        try{
            postRepository.deleteById(postId);
            return "deleted";
        } catch( Error e) {
            return "fail";
        }
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MDT')")
    @GetMapping(path = "")
    public Iterable<Post> getAllPost() {
        return postRepository.findAll();
    }

//    //TODO: need to take in post and userID, link them
//    @PostMapping(path = "link") // Map ONLY POST Requests
//    public String linkPostToUser(@RequestBody String body) {
////        PersistUser user = userRepository.findById(0).get();
//        PersistUser user = new PersistUser("postuser","password","PATIENT",1,1,1,1);
//        Post post = new Post("joinTitle", "joinDesc", "text", "joinCat", "www.google.com", "www.google.com");
//        user.getPosts().add(post);
//        userRepository.save(user);
//        return "hello";
//    }

    //Works for one-to-many
//    @PostMapping(path = "") // Map ONLY POST Requests
//    public @ResponseBody
//    String createPost(@RequestBody String body) {
//        PersistUser user = userRepository.findById(6).get();
//        Post post = new Post("video", "joinCat", "joinTitle", "joinDesk", "joinlinkUrl", "joinImageUrl", user);
//        postRepository.save(post);
//        return "TODO";
//    }

    @GetMapping(path = "/{postId}")
    public Post getPost(@PathVariable("postId") Integer postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalStateException("Post (" + postId + ") doesn't exist"));
//                POSTS.stream()
//                .filter(post -> postId.equals(post.getId()))
//                .findFirst()
//                .orElseThrow(() ->new IllegalStateException("student " + postId + "doesn't exist"));
    }
}
