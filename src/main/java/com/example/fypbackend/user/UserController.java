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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

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
    public @ResponseBody
    String createUser(@RequestBody String body) {
//        System.out.println(body);
        Gson gson = new Gson();
        PersistUser user = gson.fromJson(body, PersistUser.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLoginattempts(0);
//        System.out.println(user);
        persistUserRepository.save(user);
        return "Saved new user";
    }

    @PutMapping(path = "")
    public @ResponseBody
    String updateUser(@RequestBody String body) {
//        System.out.println("test before Gson:\n " + body + "\n");

        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = (Map<String, Object>) gson.fromJson(body, map.getClass());

        String username = (String) map.get("username");
        String role = (String) map.get("role");
        int id = (int) Math.round((Double) map.get("id"));
        int accountNonExpired = (int) Math.round((Double) map.get("accountNonExpired"));
        int accountNonLocked = (int) Math.round((Double) map.get("accountNonLocked"));
        int credentialsNonExpired = (int) Math.round((Double) map.get("credentialsNonExpired"));
        int enabled = (int) Math.round((Double) map.get("enabled"));

        PersistUser user = persistUserRepository.findById(id).get();

        user.setId(id);
        user.setAccountNonExpired(accountNonExpired);
        user.setAccountNonLocked(accountNonLocked);
        user.setCredentialsNonExpired(credentialsNonExpired);
        user.setEnabled(enabled);
        user.setLoginattempts(0);

        persistUserRepository.save(user);
        return "Updated User";
    }

    @GetMapping(path = "/admin/{userId}")
    public String adminGetUser(@PathVariable("userId") Integer userId) {
        PersistUser user = persistUserRepository.findById(userId).get();
//        \"id\":"+user.getId()+",
//        System.out.println(userId);
//        System.out.println(  "{\"username\":\""+user.getUsername()+"\",\"password\":\""+user.getPassword()+"\",\"role\":\""+user.getRole()+"\",\"accountNonExpired\":"+user.getAccountNonExpired()+",\"accountNonLocked\":"+user.getAccountNonLocked()+", \"credentialsNonExpired\":"+user.getCredentialsNonExpired()+",\"enabled\":"+user.getEnabled()+"}");

        return "{\"id\":" + user.getId() + ", \"username\":\"" + user.getUsername().trim() + "\",\"password\":\"" + user.getPassword() + "\",\"role\":\"" + user.getRole() + "\",\"accountNonExpired\":" + user.getAccountNonExpired() + ",\"accountNonLocked\":" + user.getAccountNonLocked() + ", \"credentialsNonExpired\":" + user.getCredentialsNonExpired() + ",\"enabled\":" + user.getEnabled() + "}";
//        return persistUserRepository.findById(userId).get();
    }


    /**
     * localhost:8080/groups/58/posts/0
     **/
    @PostMapping(path = "/{userId}/hiddenposts/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MDT','ROLE_PATIENT')")
    public String hidePostFromGroup(@PathVariable("userId") Integer userId, @PathVariable("postId") Integer postId) {
        Groups group = groupsRepository.findMdtByPatientId(userId);
        Post post = postRepository.findById(postId).get();
//        System.out.println(""+ group.id + " " + postId);
        group.getPosts().remove(post);
        group.getHiddenposts().add(post);
        groupsRepository.save(group);
        return "Linked post to group";
    }

    @GetMapping(path = "/{userId}/posts")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MDT','ROLE_PATIENT')")
    public Iterable<Post> getUserPosts(@PathVariable("userId") Integer userId) {
//        System.out.println("UserController.getUserPosts(): getting user#" + userId + "'s posts");
//        System.out.println(postRepository.findUserPosts(userId));
        return postRepository.findUserPosts(userId);
    }

    @GetMapping(path = "/{id}/groups")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MDT','ROLE_PATIENT')")
    public Groups getGroup(@PathVariable("id") Integer id) {
        if (groupsRepository.findById(groupsRepository.getGroupIdByUserId(id)).isPresent()) {
            return groupsRepository.findById(groupsRepository.getGroupIdByUserId(id)).get();
        } else {
            return null;
        }
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
        List<Patient> patients = allAssignedPatientsBefore.stream()
                .map(x -> new Patient(x.getId(), x.getUsername(), groupsRepository.getGroupIdByUserId(x.getId()))).collect(Collectors.toList());
        patients.addAll(allUnassignedPatientsBefore.stream()
                .map(x -> new Patient(x.getId(), x.getUsername(), null)).collect(Collectors.toList()));
        return patients;
    }


    @DeleteMapping(path = "/{userId}")
    public @ResponseBody String deleteUser(@PathVariable("userId") Integer userId) {
        try{
            persistUserRepository.deleteById(userId);
            return "deleted";
        } catch( Error e) {
            return "fail";
        }
    }


    @PostMapping(path = "/{userId}/patienthiddenposts/{postId}")
    public String patientPostNotVisibleToGroup(@PathVariable("userId") Integer userId, @PathVariable("postId") Integer postId) {
        System.out.println(userId);
        System.out.println(postId);

        Groups group = groupsRepository.findMdtByPatientId(userId);
        Post post = postRepository.findById(postId).get();
//        System.out.println(""+ group.id + " " + postId);
        group.getPosts().remove(post);
        group.getHiddenposts().add(post);
        groupsRepository.save(group);
        return "Linked post to group";
    }

    @PostMapping(path = "/{userId}/patientvisibleposts/{postId}")
    public String patientPostVisibleToGroup(@PathVariable("userId") Integer userId, @PathVariable("postId") Integer postId) {
        Groups group = groupsRepository.findMdtByPatientId(userId);
        Post post = postRepository.findById(postId).get();
//        System.out.println(""+ group.id + " " + postId);
        group.getPosts().add(post);
        group.getHiddenposts().remove(post);
        groupsRepository.save(group);
        return "Linked post to group";
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