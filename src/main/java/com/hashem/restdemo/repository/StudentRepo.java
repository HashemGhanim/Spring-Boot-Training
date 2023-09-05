package com.hashem.restdemo.repository;

import com.hashem.restdemo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepo extends JpaRepository<Student , Integer> {
}
