package com.hashem.restdemo.jobs.service;

import com.hashem.restdemo.jobs.EmailJob;
import com.hashem.restdemo.model.Course;
import com.hashem.restdemo.model.Lesson;
import com.hashem.restdemo.model.Student;
import com.hashem.restdemo.repository.LessonRepo;
import com.hashem.restdemo.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmailJobService {

    @Autowired
    private LessonRepo lessonRepo;

    @Autowired
    private Scheduler scheduler;

    public void scheduleJobs(LocalDateTime localDateTime , int lessonId) throws SchedulerException {
        ZonedDateTime dateTime = ZonedDateTime.of(localDateTime , ZoneId.of("Asia/Gaza"));

        dateTime = dateTime.minusMinutes(15);

        if(dateTime.isBefore(ZonedDateTime.now())){
            return;
        }


        JobDetail jobDetail = buildJob(lessonId);
        Trigger trigger = buildTrigger(jobDetail , dateTime);

        scheduler.scheduleJob(jobDetail , trigger);

    }


    private JobDetail buildJob(int lessonId){
        Lesson lesson = lessonRepo.findById(lessonId).orElse(null);
        Course course = lesson.getCourse();
        Set<Student> students = course.getStudents();

        List<String> emails = students.stream().map(student ->  student.getUserStudent().getEmail()).collect(Collectors.toList());
        List<String> names = students.stream().map(student -> student.getFirstName()).collect(Collectors.toList());

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("emails" , emails);
        jobDataMap.put("studentNames" , names);
        jobDataMap.put("lesson" , course.getCourseName());
        return JobBuilder.newJob(EmailJob.class)
                .usingJobData(jobDataMap)
                .withIdentity(UUID.randomUUID().toString() , "email-job")
                .storeDurably()
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail , ZonedDateTime startAtTime){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName() , "email-trigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .startAt(Date.from(startAtTime.toInstant()))
                .build();
    }
}
