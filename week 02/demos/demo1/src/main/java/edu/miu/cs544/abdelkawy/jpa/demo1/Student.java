package edu.miu.cs544.abdelkawy.jpa.demo1;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private float gpa;



    //@OneToMany(cascade = CascadeType.PERSIST)
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Course> courses;

    public Student() {
    }

    public Student(Long id, String name, float gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;

        this.courses = new ArrayList<>();
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

    public List<Course> getCourses() {
        return new ArrayList<>(this.courses);
    }

    public void addCourse(Course course) {
        if (course != null) {
            this.courses.add(course);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gpa=" + gpa +
                ", courses=" + courses +
                '}';
    }
}
