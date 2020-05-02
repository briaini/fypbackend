package com.example.fypbackend.user;

import com.example.fypbackend.auth.Patient;
import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.auth.PersistUserRepository;
import com.example.fypbackend.comment.CommentRepository;
import com.example.fypbackend.posts.Post;
import com.example.fypbackend.posts.PostRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MDT')")
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

    /**
     localhost:8080/groups/58/posts/0
     **/
    @PostMapping(path = "/{userId}/hiddenposts/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MDT','ROLE_PATIENT')")
    public String hidePostFromGroup(@PathVariable("userId") Integer userId, @PathVariable("postId") Integer postId) {
        Groups group = groupsRepository.findMdtByPatientId(userId);
        Post post = postRepository.findById(postId).get();
        System.out.println(""+ group.id + " " + postId);
        group.getPosts().remove(post);
        group.getHiddenposts().add(post);
        groupsRepository.save(group);
        return "Linked post to group";
    }

    @GetMapping(path = "/{userId}/posts")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MDT','ROLE_PATIENT')")
    public Iterable<Post> getUserPosts(@PathVariable("userId") Integer userId) {
        System.out.println("UserController.getUserPosts(): getting user#" + userId + "'s posts");
        System.out.println(postRepository.findUserPosts(userId));
        return postRepository.findUserPosts(userId);
    }

    @GetMapping(path = "/{id}/groups")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MDT','ROLE_PATIENT')")
    public Groups getGroup(@PathVariable("id") Integer id) {
        return groupsRepository.findById(groupsRepository.getGroupIdByUserId(id)).get();
    }

    @PostMapping(path = "/{patientId}/posts/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MDT','ROLE_PATIENT')")
    public String linkPostToPatient(@PathVariable("patientId") Integer patientId, @PathVariable("postId") Integer postId) {
        Groups group = groupsRepository.findById(groupsRepository.getGroupIdByUserId(patientId)).get();
        Post post = postRepository.findById(postId).get();
        group.getPosts().add(post);
        groupsRepository.save(group);
        return "Linked post to patient";
    }

    @GetMapping(path = "/patients")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MDT','ROLE_PATIENT')")
    public List<Patient> getAllPatients() {
        List<PersistUser> allAssignedPatientsBefore = persistUserRepository.getAllAssignedPatients();
        List<PersistUser> allUnassignedPatientsBefore = persistUserRepository.getAllUnassignedPatients();
        System.out.println("un" +allUnassignedPatientsBefore);
        System.out.println("A" +allAssignedPatientsBefore);

        List<Patient> patients = allAssignedPatientsBefore.stream()
                .map(x -> new Patient(x.getId(), x.getUsername(), groupsRepository.getGroupIdByUserId(x.getId()))).collect(Collectors.toList());
        patients.addAll(allUnassignedPatientsBefore.stream()
                .map(x -> new Patient(x.getId(), x.getUsername(), null)).collect(Collectors.toList()));
        return patients;
    }

//    @GetMapping(path = "/unassignedPatients")
//    public List<Patient> getUnassignedPatients() {
//        List<PersistUser> allUnassignedPatientsBefore = persistUserRepository.getAllUnassignedPatients();
//        System.out.println("un" +allUnassignedPatientsBefore);
//
//        List<Patient> patients = allUnassignedPatientsBefore.stream()
//                .map(x -> new Patient(x.getId(), x.getUsername(), null)).collect(Collectors.toList());
//        return patients;
//    }





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