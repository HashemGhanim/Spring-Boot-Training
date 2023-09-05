package com.hashem.restdemo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hashem.restdemo.security.model.User;
import com.hashem.restdemo.validation.IsValidName;
import com.hashem.restdemo.validation.IsValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    @NotNull(message = "First Name Of Student Should Be Not Null")
    @NotEmpty(message = "First Name Of Student Should Be Not Empty")
    @IsValidName(message = "First Name Should be initials with characters a-z Or A-Z")
    private String firstName;

    @IsValidName(message = "Last Name Should be initials with characters a-z Or A-Z")
    private String lastName;

    @NotNull(message = "Phone Should Be Not Null")
    @NotEmpty(message = "Phone Should Be Not Empty")
    private String phone;



    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userStudent;



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "courseId")
    )
    private Set<Course> enrolledCourses = new HashSet<>();

    public void enrollCourse(Course course){
        enrolledCourses.add(course);
    }

    public void unEnrollCourse(Course course){
        enrolledCourses.remove(course);
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Set<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public User getUserStudent() {
        return userStudent;
    }

    public void setUserStudent(User userStudent) {
        this.userStudent = userStudent;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", enrolledCourses=" + enrolledCourses +
                '}';
    }
}
