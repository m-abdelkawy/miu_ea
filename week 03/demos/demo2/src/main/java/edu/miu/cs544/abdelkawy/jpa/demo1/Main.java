package edu.miu.cs544.abdelkawy.jpa.demo1;

import jakarta.persistence.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("start");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demo1_pu");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query query =em.createNamedQuery("Student.findByCourseCapacity", Student.class);
        query.setParameter("courseCapacity", 81);

        List<Student> students= query.getResultList();

        students.forEach(s->{
            System.out.println(s);
        });



        tx.commit();

        em.close();
        emf.close();
        System.out.println("End!");
    }

    public static void addUsersAndCourses(EntityManager em) {


        Student john = new Student(1L, "John Smith", 3.85f);
        Student Ahmed = new Student(2L, "Ahmed ALi", 3.95f);
        Student Sarah = new Student(3L, "Sarah Ahmed", 3.87f);

        Course cs544 = new Course(1L, "Enterprise Architecture", 100);
        Course cs572 = new Course(2L, "Modern Web Applications", 100);
        Course cs422 = new Course(3L, "DBMS", 80);

        john.setCourse(cs544);
        Ahmed.setCourse(cs572);
        Sarah.setCourse(cs422);

        em.persist(john);
        em.persist(Ahmed);
        em.persist(Sarah);

        // because of persist cascade type
//        em.persist(cs544);
//        em.persist(cs572);
//        em.persist(cs422);


    }
}
