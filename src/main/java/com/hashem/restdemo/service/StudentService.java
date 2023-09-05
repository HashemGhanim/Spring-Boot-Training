package com.hashem.restdemo.service;

import com.hashem.restdemo.exception.BadRequestException;
import com.hashem.restdemo.model.Course;
import com.hashem.restdemo.model.Student;
import com.hashem.restdemo.repository.StudentRepo;
import com.hashem.restdemo.repository.UserRepo;
import com.hashem.restdemo.security.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private UserRepo userRepo;


    @Cacheable(value = "getAllStudents" , key = "#root.methodName")
    public List<Student> getAllStudents(){
        return studentRepo.findAll();
    }

    @Cacheable(value = "getStudent" , key = "#studentId")
    public Optional<Student> getStudent(int studentId) {
        return studentRepo.findById(studentId);
    }

    @Transactional
    @CacheEvict(value = {"getAllStudents" , "getStudent" , "getAllCoursesForStudent"} , key = "#student.studentId" , allEntries = true)
    public Student setStudent(@Valid Student student, @Valid User user) {

        User isUserInfoAlreadyExist = userRepo.findByEmail(user.getEmail()).orElse(null);

        if(isUserInfoAlreadyExist != null){
            throw new BadRequestException("This Email is already exist, Please Try again .. !");
        }

        Student studentSaved = studentRepo.save(student);
        userRepo.save(user);
        return studentSaved;
    }


    @CacheEvict(value = {"getAllStudents" , "getStudent" , "getAllCoursesForStudent"} , key = "#studentId" , allEntries = true)
    public void deleteStudent(int studentId) {
         studentRepo.deleteById(studentId);
    }

    @Transactional
    @CacheEvict(value = {"getAllStudents" , "getStudent" , "getAllCoursesForStudent"} , key = "#studentId" , allEntries = true)
    public Student updateStudent(Student student , User user , int studentId){
        Student mainStudent  = studentRepo.findById(studentId).orElse(null);

        if(mainStudent == null){
            throw new BadRequestException("This Student Is not exist so ... try again please .. !");
        }

        User isUserInfoAlreadyExist = userRepo.findByEmail(user.getEmail()).orElse(null);

        if(isUserInfoAlreadyExist != null && !isUserInfoAlreadyExist.getEmail().equals(mainStudent.getUserStudent().getEmail())){
            throw new BadRequestException("This Email is already exist, Please Try again .. !");
        }

        mainStudent.setPhone(student.getPhone());
        mainStudent.setLastName(student.getLastName());
        mainStudent.setFirstName(student.getFirstName());

        User userForStudent = mainStudent.getUserStudent();

        userForStudent.setEmail(user.getEmail());
        userForStudent.setPassword(user.getPassword());

        mainStudent.setUserStudent(userForStudent);

        userRepo.save(userForStudent);
        return studentRepo.save(mainStudent);
    }

    @Cacheable(value = "getAllCoursesForStudent" , key = "#studentId")
    public Set<Course> getAllCoursesForStudent(int studentId) {
        Student student = studentRepo.findById(studentId).orElse(new Student());

        return student.getEnrolledCourses();
    }

}
