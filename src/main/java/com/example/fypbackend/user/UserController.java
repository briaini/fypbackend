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
    @Autowired
    GroupsRepository groupsRepository;

    @GetMapping(path = "")
    public Iterable<PersistUser> getAllUsers() {
        return persistUserRepository.findAll();
    }

    @PostMapping(path = "/userID")
    public Integer getUserId(@RequestBody String username) {
        return persistUserRepository.getUserId(username);
    }

/*
    {
        "username": "patient2@gmail.com",
            "password": "Password1",
            "role": "PATIENT",
            "accountNonExpired": 1,
            "accountNonLocked": 1,
            "credentialsNonExpired": 1,
            "enabled": 1
    }
*/
    @PostMapping(path = "")
    public @ResponseBody String createUser(@RequestBody String body) {
        Gson gson = new Gson();
        PersistUser user = gson.fromJson(body, PersistUser.class);
        persistUserRepository.save(user);
        return "Saved new user";
    }

    @GetMapping(path = "/{userId}/posts")
    public Iterable<Post> getUserPosts(@PathVariable("userId") Integer userId) {
        System.out.println("UserController.getUserPosts(): getting user#" + userId + "'s posts");
        System.out.println(postRepository.findUserPosts(userId));
        return postRepository.findUserPosts(userId);
    }

    @GetMapping(path = "/{id}/groups")
    public Groups getGroup(@PathVariable("id") Integer id) {
        return groupsRepository.findById(groupsRepository.getGroupByUserId(id)).get();
    }

    @PostMapping(path = "/{patientId}/posts/{postId}")
    public String linkPostToPatient(@PathVariable("patientId") Integer patientId, @PathVariable("postId") Integer postId) {
        Groups group = groupsRepository.findById(groupsRepository.getGroupByUserId(patientId)).get();
        Post post = postRepository.findById(postId).get();
        group.getPosts().add(post);
        groupsRepository.save(group);
        return "Linked post to patient";
    }




    // old methods below

//    @GetMapping(path = "/{mdtId}/patients")
//    public List<Patient> getPatients(@PathVariable("mdtId") Integer mdtId) {
//        List<PersistUser> patientsBefore = persistUserRepository.findById(mdtId).get().getPatients();
//        List<Patient> patients = patientsBefore.stream()
//                .map(x -> new Patient(x.getId(), x.getUsername(), x.getPosts().stream().map(post -> post.getId()).collect(Collectors.toList()), commentRepository.findByUserId(x.getId()), x.getProfessionals().stream().map(pro -> pro.getId()).collect(Collectors.toList())))
//                .collect(Collectors.toList());
//        return patients;
//    }
//
//    //    @PreAuthorize("hasAuthority('patient:write')")
//    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @PostMapping(path = "/{patientId}/users/{mdtId}")
//    public String linkPatientToMDT(@PathVariable("patientId") Integer patientId, @PathVariable("mdtId") Integer mdtId) {
//        PersistUser patient = persistUserRepository.findById(patientId).get();
//        PersistUser mdtMember = persistUserRepository.findById(mdtId).get();
//        patient.getProfessionals().add(mdtMember);
//        persistUserRepository.save(patient);
//        return "Added mdtMember to patient";
//    }

//    @PostMapping(path = "/{patientId}/posts/{postId}")
//    public String linkPostToPatient(@PathVariable("patientId") Integer patientId, @PathVariable("postId") Integer postId) {
//        PersistUser user = persistUserRepository.findById(patientId).get();
//        user.getPosts().add(postRepository.findById(postId).get());
//        persistUserRepository.save(user);
//        return "Linked post to patient";
//    }
}