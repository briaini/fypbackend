package com.example.fypbackend.user;

import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.auth.PersistUserRepository;
import com.example.fypbackend.posts.Post;
import com.example.fypbackend.posts.PostRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    PersistUserRepository persistUserRepository;
    @Autowired
    GroupsRepository groupsRepository;
    @Autowired
    PostRepository postRepository;

    /**
     {
     "name":"group1"
     }
    **/
    @PostMapping(path = "/")
    public @ResponseBody
    String createGroup(@RequestBody String body) {
        Gson gson = new Gson();
        Groups group = gson.fromJson(body, Groups.class);
        groupsRepository.save(group);
        return "Saved new group";
    }

    /**
        localhost:8080/groups/58/users/0
     **/
    @PostMapping(path = "/{groupId}/users/{userId}")
    public String linkUserToGroup(@PathVariable("userId") Integer patientId, @PathVariable("groupId") Integer groupId) {
        PersistUser patient = persistUserRepository.findById(patientId).get();
        Groups group = groupsRepository.findById(groupId).get();
        patient.getGroups().add(group);
        persistUserRepository.save(patient);
        return "Added user to group";
    }


    /**
        localhost:8080/groups/58/posts/0
     **/
    @PostMapping(path = "/{groupId}/posts/{postId}")
    public String linkPostToGroup(@PathVariable("groupId") Integer groupId, @PathVariable("postId") Integer postId) {
        Groups group = groupsRepository.findById(groupId).get();
        Post post = postRepository.findById(postId).get();
        group.getPosts().add(post);
        groupsRepository.save(group);
        return "Linked post to group";
    }
}
