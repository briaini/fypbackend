package com.example.fypbackend.user;

import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.auth.PersistUserRepository;
import com.example.fypbackend.comment.Comment;
import com.example.fypbackend.comment.CommentRecipient;
import com.example.fypbackend.comment.CommentRecipientRepository;
import com.example.fypbackend.comment.CommentRepository;
import com.example.fypbackend.posts.Post;
import com.example.fypbackend.posts.PostRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    PersistUserRepository persistUserRepository;
    @Autowired
    GroupsRepository groupsRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentRecipientRepository commentRecipientRepository;

    @GetMapping
    public Iterable<Groups> getAllGroups() {
        return groupsRepository.findAll();
    }


    /**
     localhost:8080/groups

     {
     "name":"group2",
     "isMdt":true       ....optional * need to set isMdt to true for patient group
     }
    **/
    @PostMapping(path = "")
    public @ResponseBody
    String createGroup(@RequestBody String body) {
        Gson gson = new Gson();
        Groups group = gson.fromJson(body, Groups.class);
        Groups savedGroup = groupsRepository.save(group);

//        System.out.println("tset create group");
//        System.out.println(savedGroup.getId());
        CommentRecipient commentRecipient = new CommentRecipient();
        commentRecipient.setRecipientGroup(savedGroup);
        commentRecipientRepository.save(commentRecipient);
        return "Saved new group";
    }

    @DeleteMapping(path = "/{groupId}")
    public @ResponseBody String deleteGroup(@PathVariable("groupId") Integer groupId) {
        try{
            groupsRepository.deleteById(groupId);
            return "deleted";
        } catch( Error e) {
            return "fail";
        }
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

    /*
            localhost:8080/groups/1/comments

     {
        "textBody":"this is a comment",
        "postId":1,
        "subjectId":1
     }

     */
    @PostMapping(path = "/{groupID}/comments")
    public String commentToGroup(@RequestBody String body, @PathVariable("groupID") Integer groupID) {
//        System.out.println(commentRecipientRepository.findAll().toString());

//        System.out.println("------------------------------------");
//        System.out.println(body);

        CommentRecipient recipient = commentRecipientRepository.getByGroupId(groupID);

//        CommentRecipient recipient = commentRecipientRepository.findById(recipientId).get();
        Set<Comment> commentSet = recipient.getComments();
//
        Gson gson = new Gson();
        Comment comment = gson.fromJson(body, Comment.class);
        comment.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        comment.setCommentRecipient(recipient);

        commentSet.add(comment);
        recipient.setComments(commentSet);

        commentRecipientRepository.save(recipient);
        return "Sent comment";
    }

    @PostMapping(path = "/{groupID}/messages")
    public String messageToGroup(@RequestBody String body, @PathVariable("groupID") Integer groupID) {
//        System.out.println(commentRecipientRepository.findAll().toString());

        CommentRecipient recipient = commentRecipientRepository.getByGroupId(groupID);

//        CommentRecipient recipient = commentRecipientRepository.findById(recipientId).get();
        Set<Comment> commentSet = recipient.getComments();
//
        Gson gson = new Gson();
        Comment comment = gson.fromJson(body, Comment.class);
        comment.setCommentRecipient(recipient);

        commentSet.add(comment);
        recipient.setComments(commentSet);

        commentRecipientRepository.save(recipient);
        return "Sent comment";
    }

    @PostMapping(path = "/{groupId}/hiddenposts/{postId}")
    public String postNotVisibleToGroup(@PathVariable("groupId") Integer groupId, @PathVariable("postId") Integer postId) {
        Groups group = groupsRepository.findById(groupId).get();
        Post post = postRepository.findById(postId).get();
//        System.out.println(""+ group.id + " " + postId);
        group.getPosts().remove(post);
        group.getHiddenposts().add(post);
        groupsRepository.save(group);
        return "Linked post to group";
    }

    @PostMapping(path = "/{groupId}/visibleposts/{postId}")
    public String postVisibleToGroup(@PathVariable("groupId") Integer groupId, @PathVariable("postId") Integer postId) {
        Groups group = groupsRepository.findById(groupId).get();
        Post post = postRepository.findById(postId).get();
//        System.out.println(""+ group.id + " " + postId);
        group.getPosts().add(post);
        group.getHiddenposts().remove(post);
        groupsRepository.save(group);
        return "Linked post to group";
    }
}
