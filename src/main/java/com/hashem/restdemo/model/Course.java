package com.hashem.restdemo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hashem.restdemo.validation.MoreThanFiveHours;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;

    @NotNull(message = "Course Name Should Not be Null")
    @NotEmpty(message = "Course Name Should be filled !! ")
    private String courseName;

    @NotNull(message = "Hours should  be not Null")
    @MoreThanFiveHours
    private int courseHours;

    @NotEmpty(message = "The department should be filled not empty .. !")
    private String department;


    @JsonIgnore
    @ManyToMany(mappedBy = "enrolledCourses", cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(int courseHours) {
        this.courseHours = courseHours;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
