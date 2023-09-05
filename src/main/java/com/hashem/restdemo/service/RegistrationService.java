package com.hashem.restdemo.service;

import com.hashem.restdemo.exception.BadRequestException;
import com.hashem.restdemo.model.Course;
import com.hashem.restdemo.model.Student;
import com.hashem.restdemo.repository.CourseRepo;
import com.hashem.restdemo.repository.StudentRepo;
import com.hashem.restdemo.repository.UserRepo;
import com.hashem.restdemo.security.model.User;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class RegistrationService {

    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private UserRepo userRepo;

    @Value("${limit.course.student.number}")
    private int courseLimit;


    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = {"getAllStudents" , "getStudent" , "getAllCoursesForStudent" , "getAllCourses" , "getCourse" , "getAllStudentsInCourse"} , key = "#root.methodName" , allEntries = true)
    public Student enrollCourse(int studentId, int courseId) {
        isUser(studentId);
        Course course = courseRepo.findById(courseId).orElse(null);
        Student student = studentRepo.findById(studentId).orElse(null);

        if(student == null) {
            throw new BadRequestException("Student does not exist please try again .. !");
        }

        if(course == null){
            throw new BadRequestException("Course does not exist please try again .. !");
        }


        if(course.getStudents().size() >= courseLimit)
        {
            throw new BadRequestException("This Course is full and reach with maximum number of student .. !");
        }

        student.enrollCourse(course);

        return studentRepo.save(student);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = {"getAllStudents" , "getStudent" , "getAllCoursesForStudent" ,"getAllCourses" , "getCourse" , "getAllStudentsInCourse"} , key = "#root.methodName" , allEntries = true)
    public Student unEnrollCourse(int studentId, int courseId) {
        isUser(studentId);
        Course course = courseRepo.findById(courseId).orElse(null);
        Student student = studentRepo.findById(studentId).orElse(null);

        if(student == null) {
            throw new BadRequestException("Student does not exist please try again .. !");
        }

        if(course == null){
            throw new BadRequestException("Course does not exist please try again .. !");
        }
        student.unEnrollCourse(course);

        return studentRepo.save(student);
    }


    private void isUser(int studentId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            User user = userRepo.findByEmail(userDetails.getUsername()).orElse(null);
            if(user == null || user.getStudent().getStudentId() != studentId){
                throw new BadRequestException("You Are Not The User .. !");
            }
        }
    }
}
