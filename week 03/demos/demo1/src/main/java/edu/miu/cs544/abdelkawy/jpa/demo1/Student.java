package edu.miu.cs544.abdelkawy.jpa.demo1;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private float gpa;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "Student_Course"
            , joinColumns = @JoinColumn(name = "Student_ID", referencedColumnName = "id")
    , inverseJoinColumns = @JoinColumn(name = "Course_ID", referencedColumnName = "id", table = "Course"))
    private Course course;

    @Temporal(TemporalType.DATE)
    private Date dob;

    public Student() {
    }

    public Student(Long id, String name, float gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gpa=" + gpa +
                ", courses=" + course +
                '}';
    }
}
