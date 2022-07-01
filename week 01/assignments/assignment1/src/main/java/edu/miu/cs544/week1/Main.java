package edu.miu.cs544.week1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start...");
        Car car = new Car(1L, "Toyota", "Prius", 2010, 153000);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("car_pu");
        EntityManager em = emf.createEntityManager();

        // 01. create
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(car);
        tx.commit();

        System.out.println();
        // 02. read
        Car carFromDb = em.find(Car.class, 1L);
        System.out.println("Created Car:");
        System.out.println(car);
        System.out.println();

        // 03. update
        tx.begin();
        carFromDb.setModel("Camry");
        carFromDb.setMileage(121000);
        tx.commit();
        System.out.println("After Update");
        System.out.println(carFromDb);
        System.out.println();

        // 04. delete
        tx.begin();
        em.remove(carFromDb);
        tx.commit();

        //try to read
        carFromDb = em.find(Car.class, 1L);
        if(carFromDb == null)
            System.out.println("Car deleted...");
        else
            System.out.println(carFromDb);
        // close em and emf
        em.close();
        emf.close();
        System.out.println("End...");
    }
}
