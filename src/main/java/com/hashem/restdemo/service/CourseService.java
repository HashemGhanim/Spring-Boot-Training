package com.hashem.restdemo.service;

import com.hashem.restdemo.exception.BadRequestException;
import com.hashem.restdemo.model.Course;
import com.hashem.restdemo.model.Student;
import com.hashem.restdemo.repository.CourseRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Cacheable(value = "getAllCourses" , key = "#root.methodName")
    public List<Course> getAllCourses(){
        return courseRepo.findAll();
    }

    @Cacheable(value = "getCourse" , key = "#courseId")
    public Course getCourse(int courseId){
        return courseRepo.findById(courseId).orElse(null);
    }


    @Transactional
    @CacheEvict(value = {"getAllCourses" , "getCourse" , "getAllStudentsInCourse"} , key = "#course.courseId" , allEntries = true)
    public Course setCourse(Course course){
        if(courseRepo.isCourseExist(course.getCourseName())){
            throw new BadRequestException("This Course Is Already Exist, Please Try Again .. !");
        }
        return courseRepo.save(course);
    }

    @CacheEvict(value = {"getAllCourses" , "getCourse" , "getAllStudentsInCourse"} , key = "#courseId" , allEntries = true)
    public void deleteCourse(int courseId){
        courseRepo.deleteById(courseId);
    }

    @Transactional
    @CacheEvict(value = {"getAllCourses" , "getCourse" , "getAllStudentsInCourse"} , key = "#courseId" , allEntries = true)
    public Course updateCourse(Course course , int courseId){
        Course mainCourse = courseRepo.findById(courseId).get();
        if(mainCourse == null){
            throw new BadRequestException("This Course Is not exist so ... try again please .. !");
        }
        if(!course.getCourseName().equals(mainCourse.getCourseName()) && courseRepo.isCourseExist(course.getCourseName())){
            throw new BadRequestException("This Course Is Already Exist, Please Try Again .. !");
        }

        mainCourse.setCourseHours(course.getCourseHours());
        mainCourse.setCourseName(course.getCourseName());

        return courseRepo.save(mainCourse);
    }

    @Cacheable(value = "getAllStudentsInCourse" , key = "#courseId")
    public Set<Student> getAllStudentsInCourse(int courseId){
        Course course = courseRepo.findById(courseId).orElse(new Course());

        return course.getStudents();
    }


}
