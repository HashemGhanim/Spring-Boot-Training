package com.hashem.restdemo.controller;


import com.hashem.restdemo.model.Lesson;
import com.hashem.restdemo.service.LessonService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
@PreAuthorize("hasRole('ADMIN')")
public class LessonController {

    @Autowired
    private LessonService lessonService;


    @GetMapping
    public List<Lesson> getAllLessons(){
        return lessonService.getAllLessons();
    }

    @GetMapping("/{lessonId}")
    public Lesson getLesson(@PathVariable int lessonId){
        return lessonService.getLesson(lessonId).orElse(null);
    }

    @PostMapping("/{courseId}")
    public Lesson setLesson(@RequestBody Lesson lesson , @PathVariable int courseId) throws SchedulerException {
        return lessonService.setLesson(lesson , courseId);
    }

    @DeleteMapping("/{lessonId}")
    public void deleteLesson(@PathVariable int lessonId){
        lessonService.deleteLesson(lessonId);
    }

    @PutMapping("/{lessonId}")
    public Lesson updateLesson(@RequestBody Lesson lesson , @PathVariable int lessonId){
        return lessonService.updateLesson(lesson, lessonId);
    }

}
