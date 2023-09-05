package com.hashem.restdemo.controller;


import com.hashem.restdemo.model.Course;
import com.hashem.restdemo.model.Student;
import com.hashem.restdemo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public Course getCourse(@PathVariable int courseId){
        return courseService.getCourse(courseId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') && hasAuthority('admin:create')")
    public Course setCourse(@RequestBody @Valid  Course course){
        return courseService.setCourse(course);
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCourse(@PathVariable int courseId){
        courseService.deleteCourse(courseId);
    }

    @PutMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN') && hasAuthority('admin:update')")
    public Course updateCourse(@RequestBody @Valid Course course , @PathVariable int courseId){
        return courseService.updateCourse(course , courseId);
    }

    @GetMapping("/{courseId}/students")
    @PreAuthorize("hasRole('ADMIN') && hasAuthority('admin:read')")
    public Set<Student> getAllStudentsInCourse(@PathVariable int courseId){
        return courseService.getAllStudentsInCourse(courseId);
    }
}
