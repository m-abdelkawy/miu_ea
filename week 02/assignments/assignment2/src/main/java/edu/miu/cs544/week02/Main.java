package edu.miu.cs544.week02;

import edu.miu.cs544.week02.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start...");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("car_pu2");
        EntityManager em = emf.createEntityManager();

        Address address1 = new Address("302 N 4th St.", "Iowa City", "57898");
        Address address2 = new Address("311 S 6th St.", "Des Moines", "57887");
        Address address3 = new Address("408 S 5th St.", "Cedar Rapids", "59654");
        Address address4 = new Address("512 N 6th St.", "Ottumwa", "52265");

        Company insStateFarm = new InsuranceCompany("state Farm", address1, 4);
        Company insProgressive = new InsuranceCompany("Progressive", address2, 3);
        Company mfrToyota = new CarManufacturer("Toyota", address3, 1050, 1942);
        Company mfrHonda = new CarManufacturer("Honda", address4, 2500, 1925);

        Car toyotaPrius1 = new Car("Prius", 2022, mfrToyota, insStateFarm);
        mfrToyota.addCar(toyotaPrius1);
        insStateFarm.addCar(toyotaPrius1);

        Car accord1 = new Car("Accord", 2022, mfrHonda, insStateFarm);
        mfrHonda.addCar(accord1);
        insStateFarm.addCar(accord1);

        Car avalon = new Car("Avalon", 2010, mfrToyota, insProgressive);
        mfrToyota.addCar(avalon);
        insProgressive.addCar(avalon);

        Person person1 = new Person("Ahmed ALi", "10.05.1992");
        Person person2 = new Person("Mohammed Abdelkawy", "10.05.1993");
        Person person3 = new Person("Mohamed Adel", "10.05.1994");

        Person person4 = new Person("Mohamed Hammam", "10.05.1991");
        Person person5 = new Person("Ashraf Adel", "10.05.1989");
        Person person6 = new Person("Abdurrahman Emam", "10.05.1987");

        toyotaPrius1.addOwner(person1);
        toyotaPrius1.addDriver(person4);

        accord1.addOwner(person1);
        accord1.addOwner(person2);
        accord1.addDriver(person4);
        accord1.addDriver(person5);

        avalon.addOwner(person2);
        avalon.addDriver(person4);
        avalon.addDriver(person5);
        avalon.addDriver(person6);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(toyotaPrius1);
        em.persist(accord1);
        em.persist(avalon);

        em.persist(insStateFarm);
        em.persist(insProgressive);
        em.persist(mfrToyota);
        em.persist(mfrHonda);


        em.persist(person1);
        em.persist(person2);
        em.persist(person3);
        em.persist(person4);
        em.persist(person5);
        em.persist(person6);


        tx.commit();

        System.out.println("End...");
    }
}
