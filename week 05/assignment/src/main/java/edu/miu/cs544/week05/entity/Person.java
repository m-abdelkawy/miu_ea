package edu.miu.cs544.week05.entity;

import edu.miu.cs544.week05.entity.listeners.PersonDeleteListener;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({
        PersonDeleteListener.class
})
//@DiscriminatorColumn(name = "P_TYPE")
//@DiscriminatorValue("PERSON")
public abstract class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDate dob;
    private int age;

    @Version
    private Long version;

    public Person() {
    }

    public Person(String name, LocalDate dob) {
        this.name = name;
        this.dob = dob;
        this.age = Period.between(dob, LocalDate.now()).getYears();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
