package com.hashem.restdemo.controller;

import com.hashem.restdemo.exception.BadRequestException;
import com.hashem.restdemo.model.Course;
import com.hashem.restdemo.model.Student;
import com.hashem.restdemo.security.auth.Register;
import com.hashem.restdemo.security.model.Role;
import com.hashem.restdemo.security.model.User;
import com.hashem.restdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'STUDENT')")
    public Optional<Student> getStudent(@PathVariable int studentId){
        return studentService.getStudent(studentId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Student setStudent(@RequestBody @Valid Register register) throws BadRequestException {
        Role role = Role.STUDENT;
        User user = User.builder()
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(role)
                .build();

        Student student = Student.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .phone(register.getPhone())
                .userStudent(user)
                .build();

        return studentService.setStudent(student , user);
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void  deleteStudent(@PathVariable int studentId){
         studentService.deleteStudent(studentId);
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'STUDENT')")
    public Student updateStudent(@RequestBody @Valid Register register , @PathVariable int studentId){
        Role role = Role.STUDENT;
        User user = User.builder()
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(role)
                .build();

        Student student = Student.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .phone(register.getPhone())
                .userStudent(user)
                .build();
        return studentService.updateStudent( student , user , studentId);
    }


    @GetMapping("/{studentId}/courses")
    @PreAuthorize("hasAnyRole('ADMIN' , 'STUDENT')")
    public Set<Course> getAllCoursesForStudent(@PathVariable int studentId) {
        return studentService.getAllCoursesForStudent(studentId);
    }

}
