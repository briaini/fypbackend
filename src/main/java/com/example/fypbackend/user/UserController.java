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

    @GetMapping(path = "/{userId}/posts")
    public Iterable<Post> getUserPosts(@PathVariable("userId") Integer userId) {
//    public String getUserPosts(@PathVariable("userId") Integer userId) {
        System.out.println("UserController.getUserPosts(): getting user#" + userId + "'s posts");
        System.out.println(postRepository.findUserPosts(userId));
        return postRepository.findUserPosts(userId);

//        postRepository.findUserPosts(userId).forEach((x)->{
////            x.setCom
////            commentRepository.
//            System.out.println(x.toString());
//        });
//        return postRepository.findUserPosts(userId);
    }


//    @GetMapping(path = "/test")
////    public String testingUserId(@PathVariable("userId") String username) {
//    public String testingUserId() {
//            System.out.print("UserController.testing(): getting user " + "username" + "s id");
//        return persistUserRepository.getUserId("rose");
//    }

//    @PostMapping
////    @PreAuthorize("hasAuthority('student:write')")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    public void registerNewStudent(@RequestBody Student student) {
//        System.out.println(student);
//        System.out.println("registernewStudent");
//    }
//
//    @DeleteMapping(path = "{studentId}")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    public void deleteStudent (@PathVariable("studentId") Integer studentId) {
//        System.out.println(studentId);
//        System.out.println("deleteStudent");
//    }

}