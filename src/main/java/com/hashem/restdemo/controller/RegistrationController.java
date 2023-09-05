package com.hashem.restdemo.controller;

import com.hashem.restdemo.model.Student;
import com.hashem.restdemo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reg")
public class RegistrationController {

    @Autowired
    private  RegistrationService registrationService;


    @PostMapping("/{studentId}/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public Student enrollCourse(@PathVariable int studentId , @PathVariable int courseId){
         return registrationService.enrollCourse(studentId, courseId);
    }

    @DeleteMapping ("/{studentId}/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public Student unEnrollCourse(@PathVariable int studentId , @PathVariable int courseId){
        return registrationService.unEnrollCourse(studentId, courseId);
    }
}
