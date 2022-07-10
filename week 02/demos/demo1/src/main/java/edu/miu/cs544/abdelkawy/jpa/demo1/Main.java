package edu.miu.cs544.abdelkawy.jpa.demo1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        System.out.println("start");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demo1_pu");
        EntityManager em = emf.createEntityManager();

        Student student = new Student(1L, "Ahmed", 3.85f);

        Course cs544 = new Course(544L, "Enterprise Architecture", 100);
        student.addCourse(cs544);
        Course cs422 = new Course(422L, "Database Management Systems", 85);
        student.addCourse(cs422);
        Course cs575 = new Course(575L, "Modern Web Applications", 90);
        student.addCourse(cs575);


//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        em.persist(student);
//        tx.commit();

        Student student1 = em.find(Student.class, 1L);
        System.out.println(student1);

        em.close();
        emf.close();
        System.out.println("End!");
    }
}
