package com.example.fypbackend.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Bob Saget"),
            new Student(2, "Emma Gemma"),
            new Student(3, "Dan Bland")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Student> getStudents() {
        System.out.println("getAllStudents");
        return STUDENTS;
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('student:write')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(student);
        System.out.println("registernewStudent");
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteStudent (@PathVariable("studentId") Integer studentId) {
        System.out.println(studentId);
        System.out.println("deleteStudent");

    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println(String.format("%s %s", studentId, student));
        System.out.println("updateStudent");
    }
}
