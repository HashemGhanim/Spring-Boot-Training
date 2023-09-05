package com.hashem.restdemo.repository;

import com.hashem.restdemo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course , Integer> {
    @Query(""+
            "SELECT CASE WHEN COUNT(c) > 0 THEN "+
            "TRUE ELSE FALSE END "+
            "FROM Course c " +
            "WHERE c.courseName = ?1")
    boolean isCourseExist(String courseName);
}
