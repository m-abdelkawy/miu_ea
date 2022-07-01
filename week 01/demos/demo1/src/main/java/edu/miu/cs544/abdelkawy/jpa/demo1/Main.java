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

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(student);
        tx.commit();

        em.close();
        emf.close();
        System.out.println("End!");
    }
}
