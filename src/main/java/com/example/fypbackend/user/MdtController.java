package com.example.fypbackend.user;

import com.example.fypbackend.auth.Patient;
import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.auth.PersistUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mdt")
public class MdtController {
    @Autowired
    PersistUserRepository persistUserRepository;
    @Autowired
    GroupsRepository groupsRepository;

//    @GetMapping(path = "/{id}/groups")
//    public String getGroups(@PathVariable("id") Integer id) {
//        return groupsRepository.getProsMdtIds(id).toString();
//    }

    @GetMapping(path = "/{id}/groups")
    public Iterable<Groups> getAllGroups(@PathVariable("id") Integer id) {
        return groupsRepository.findAll();
    }

    @GetMapping(path = "/{id}/mygroups")
    public Iterable<Groups> getGroupsByUserId(@PathVariable("id") Integer id) {
        return groupsRepository.findGroupsByUserId(id);
    }

    @GetMapping(path = "/{id}/patients")
    public List<Patient> getPatients(@PathVariable("id") Integer id) {
        List<PersistUser> patientsBefore = persistUserRepository.getPatients(id);
        List<Patient> patients = patientsBefore.stream()
                .map(x -> new Patient(x.getId(), x.getUsername(), groupsRepository.getGroupIdByUserId(x.getId()))).collect(Collectors.toList());
        return patients;
    }
}
