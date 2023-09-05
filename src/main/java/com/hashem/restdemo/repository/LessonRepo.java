package com.hashem.restdemo.repository;

import com.hashem.restdemo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LessonRepo extends JpaRepository<Lesson , Integer> {
}
