package com.example.fypbackend.user;

import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.auth.PersistUserRepository;
import com.example.fypbackend.comment.CommentRepository;
import com.example.fypbackend.posts.Post;
import com.example.fypbackend.posts.PostRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    PersistUserRepository persistUserRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    @PostMapping(path = "/userID")
    public Integer getUserId(@RequestBody String username) {
        return persistUserRepository.getUserId(username);
    }

    @PostMapping(path = "") // Map ONLY POST Requests
    public @ResponseBody
    String createUser(@RequestBody String body) {
        Gson gson = new Gson();
        PersistUser user = gson.fromJson(body, PersistUser.class);
        persistUserRepository.save(user);
        return "Saved new user";
    }

//    @GetMapping(path = "/{userId}/posts")
//    public Iterable<Post> getUserPosts(@PathVariable("userId") Integer userId) {
    @GetMapping(path = "/testes")
    public String getUserPosts() {
        System.out.println("UserController.getUserPosts(): getting user#" + 6 + "'s posts");
        System.out.println(postRepository.findUserPosts(6));

//        postRepository.findUserPosts(userId).forEach((x)->{
////            x.setCom
////            commentRepository.
//            System.out.println(x.toString());
//        });
        return "hello";
//        return postRepository.findUserPosts(userId);
    }


//    @GetMapping(path = "/test")
////    public String testingUserId(@PathVariable("userId") String username) {
//    public String testingUserId() {
//            System.out.print("UserController.testing(): getting user " + "username" + "s id");
//        return persistUserRepository.getUserId("rose");
//    }

}