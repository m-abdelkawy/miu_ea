package edu.miu.cs544.week03;

import edu.miu.cs544.week03.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start...");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("car_pu3");
        EntityManager em = emf.createEntityManager();

        populateDb(em);


        System.out.println("End...");
    }

    public static void populateDb(EntityManager em){
        // 01. Create Persons
        Owner ahmed = new Owner("Ahmed", LocalDate.of(1986, 2, 15));
        Owner mohamed = new Owner("Mohamed", LocalDate.of(1990, 4, 10));
        Owner ayman = new Owner("Ayman", LocalDate.of(1995, 5, 12));

        Driver abdurrahman = new Driver("Abdurrahman", LocalDate.of(1989, 2, 15));
        Driver amgad = new Driver("Amgad", LocalDate.of(1999, 2, 15));
        Driver salem = new Driver("Salem", LocalDate.of(1975, 2, 15));

        // 02. Create Manufacturers
        Address toyotaAddress = new Address("1", "Toyota Rd", "Plano", "TX", "75024");
        Address fordAddress = new Address("1", "American Rd", "Dearborn", "MI", "48126");
        Address nissanAddress = new Address("1", "Nissan Way", "Franklin", "TN", "37067");
        Address hondaAddress = new Address("1", "Torrance Blvd", "Torrance", "CA", "90501");

        CarManufacturer toyota = new CarManufacturer("Toyota", toyotaAddress, 250000, 1947);
        CarManufacturer ford = new CarManufacturer("Ford", fordAddress, 200000, 1950);
        CarManufacturer nissan = new CarManufacturer("Nissan", nissanAddress, 150000, 1958);
        CarManufacturer honda = new CarManufacturer("Honda", hondaAddress, 230000, 1945);

        // 02. Create Insurance companies
        Address statefarmAddress = new Address("400", "Flowers St", "Bloomington", "IL", "128934");
        Address progressivAddress = new Address("507", "Desert Rd", "Los Angeles", "CA", "90189");

        InsuranceCompany stateFarm = new InsuranceCompany("State Farm", statefarmAddress, 3);
        InsuranceCompany progressive = new InsuranceCompany("Progressive", progressivAddress, 4);

        // 03. Create Cars
        Car avalon = new Car("Avalon", 15000, 2012, toyota);
        Car camry = new Car("Camry", 160_000, 2007, toyota);
        Car fusion = new Car("Fusion", 120_000, 2008, ford);
        Car sunny = new Car("Sunny", 150_000, 2012, nissan);
        Car civic = new Car("Civic", 70_000, 2017, honda);
        Car accord = new Car("Accord", 80_000, 2015, honda);

        // 04. assign insurance company
        avalon.setInsuranceCompany(stateFarm);
        camry.setInsuranceCompany(progressive);
        fusion.setInsuranceCompany(progressive);
        civic.setInsuranceCompany(stateFarm);

        // 05. assign Owners
        avalon.addOwner(ahmed);
        camry.addOwner(ahmed);
        fusion.addOwner(mohamed);
        fusion.addOwner(ayman);
        civic.addOwner(mohamed);
        civic.addOwner(ahmed);
        civic.addOwner(ayman);

        // 06. Assign Drivers
        avalon.addDriver(abdurrahman);
        avalon.addDriver(salem);
        civic.addDriver(amgad);
        civic.addDriver(abdurrahman);
        accord.addDriver(salem);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Persist cars
        em.persist(avalon);
        em.persist(camry);
        em.persist(fusion);
        em.persist(sunny);
        em.persist(civic);
        em.persist(accord);

        // persist companies
        em.persist(toyota);
        em.persist(ford);
        em.persist(nissan);
        em.persist(honda);

        em.persist(stateFarm);
        em.persist(progressive);


        em.persist(ahmed);
        em.persist(mohamed);
        em.persist(ayman);
        em.persist(abdurrahman);
        em.persist(amgad);
        em.persist(salem);


        tx.commit();
    }
}
