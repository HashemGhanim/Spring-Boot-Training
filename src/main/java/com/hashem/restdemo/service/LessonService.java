package com.hashem.restdemo.service;

import com.hashem.restdemo.exception.BadRequestException;
import com.hashem.restdemo.jobs.service.EmailJobService;
import com.hashem.restdemo.model.Course;
import com.hashem.restdemo.model.Lesson;
import com.hashem.restdemo.repository.CourseRepo;
import com.hashem.restdemo.repository.LessonRepo;
import org.checkerframework.checker.units.qual.A;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class LessonService{

    @Autowired
    private LessonRepo lessonRepo;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EmailJobService emailJobService;

    @Cacheable(value = "getAllLessons" , key = "#root.methodName")
    public List<Lesson> getAllLessons(){
        return lessonRepo.findAll();
    }



    @Cacheable(value = "getLesson" , key = "#root.methodName")
    public Optional<Lesson> getLesson(int lessonId){
        return lessonRepo.findById(lessonId);
    }


    @Transactional
    @CacheEvict(value = {"getAllLessons" , "getLesson"} , key = "#root.methodName" , allEntries = true)
    public Lesson setLesson(Lesson lesson , int courseId) throws SchedulerException {
        Course course = courseService.getCourse(courseId);

        if(course == null){
            throw new BadRequestException("There is no Course With This Number .. !");
        }

        lesson.setCourse(course);

        Lesson savedLesson = lessonRepo.save(lesson);

        emailJobService.scheduleJobs(getLocalDateTime(lesson.getLessonDate() , lesson.getLessonTime()) , lesson.getId());

        return savedLesson;
    }


    @Transactional
    @CacheEvict(value = {"getAllLessons" , "getLesson"} , key = "#root.methodName" , allEntries = true)
    public Lesson updateLesson(Lesson lesson , int lessonId){
        Lesson preLesson = getLesson(lessonId).orElse(null);

        if(preLesson == null){
            throw new BadRequestException("This Lesson is Not Available please try again .. !");
        }
        preLesson.setNumberOfHours(lesson.getNumberOfHours());
        preLesson.setLessonDate(lesson.getLessonDate());
        preLesson.setLessonTime(lesson.getLessonTime());


        return lessonRepo.save(preLesson);
    }


    @CacheEvict(value = {"getAllLessons" , "getLesson"} , key = "#root.methodName" , allEntries = true)
    public void deleteLesson(int lessonId){
        Lesson lesson = getLesson(lessonId).orElse(null);
        if(lesson == null){
            throw new BadRequestException("This Lesson is Not Available please try again .. !");
        }

        lessonRepo.delete(lesson);
    }


    private LocalDateTime getLocalDateTime(String date , String time){
        String dateTime = date + " " + time;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime localDateTime = LocalDateTime.parse(dateTime , formatter);

        return localDateTime;
    }
}

/*
        String dateTime = date + "T" + time +":00.846";

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime localDateTime = LocalDateTime.parse(dateTime , formatter);

        return localDateTime;
 */