package edu.miu.cs544.week03;

import edu.miu.cs544.week03.entity.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start...");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("car_pu2");
        EntityManager em = emf.createEntityManager();

        populateDb(em);

        runQueries(em);

        em.close();
        emf.close();

        System.out.println("End...");
    }

    public static void runQueries(EntityManager em){
        // 01. Write a query to find all Cars in your program using JPQL
        String queryStr1 = "SELECT c FROM Car c";
        TypedQuery<Car> query1 = em.createQuery(queryStr1, Car.class);
        List<Car> allCars = query1.getResultList();
        System.out.println("All Cars Query ResultList: ");
        allCars.forEach(c->{
            System.out.println(c);
        });

        System.out.println();
        System.out.println("--------------------------------------------------");

        // 02. Write a named Query to return all People with Car mileage greater than 10000 and the person age > 20
        //String queryReturnPeopleByMileageAndAge = "SELECT o FROM Owner o JOIN o.cars c WHERE o.age <:age AND c.mileage >:mileage";

        TypedQuery<Owner> ownerTypedQuery = em.createNamedQuery("Owner.findOwnersByMileageAndAge", Owner.class);
        ownerTypedQuery.setParameter("age", 20);
        ownerTypedQuery.setParameter("mileage", 10_000);
        List<Owner> owners = ownerTypedQuery.getResultList();
        System.out.println("Owner Query ResultList: ");
        owners.forEach(o->{
            System.out.println(o);
        });
        System.out.println();
        System.out.println("--------------------------------------------------");

        // 03. Write a Criteria API query to return all Car Insurance Companies
        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<InsuranceCompany> criQuery = queryBuilder.createQuery(InsuranceCompany.class);
        Root<InsuranceCompany> root = criQuery.from(InsuranceCompany.class);
        criQuery.select(root);
        TypedQuery<InsuranceCompany> insCriQuery = em.createQuery(criQuery);
        List<InsuranceCompany> insComps = insCriQuery.getResultList();
        System.out.println("Insurance Company Query ResultList: ");
        insComps.forEach(i->{
            System.out.println(i);
        });
        System.out.println();
        System.out.println("--------------------------------------------------");

        // 04. Write a JPQL to return all Companies.
        String allCompaniesQuery = "SELECT c FROM Company c";
        TypedQuery<Company> findAllCompanies = em.createQuery(allCompaniesQuery, Company.class);
        List<Company> comps = findAllCompanies.getResultList();
        System.out.println("all Companies Query ResultList: ");
        comps.forEach(comp->{
            System.out.println(comp);
        });
        System.out.println();
        System.out.println("--------------------------------------------------");

        // 05. Write a Criteria API query to return all People younger than 25
        // and drive a Car made after 2020 the Car is manufactured
        //in a company with more than 1000 employees, and the Car is insured with a company of rating 3 or higher.
        CriteriaQuery<Driver> criQueryPerson = queryBuilder.createQuery(Driver.class);
        Root<Driver> driverRoot = criQueryPerson.from(Driver.class);

        // 01. driver born in march
        Predicate bornInMarchPredicate = queryBuilder
                .like(driverRoot.get("dob"), "%03%");

        Join<Driver, Car> joinCar = driverRoot.join("cars");

        // 02. driver drives car made after 2020
        Predicate carYearAfter2020Predicate = queryBuilder
                .ge(joinCar.get("year"), 2020);

//        Join<Car, CarManufacturer> joinManufacturer =
//                joinCar.join("carManufacturer");

        // 03. manufacturer employees > 1000
//        Predicate numEmployees1000Predicate = queryBuilder
//                .gt(joinManufacturer.get("numberOfEmployees"), 1000);

//        Predicate andPredicate = queryBuilder
//                .and(bornInMarchPredicate, carYearAfter2020Predicate, numEmployees1000Predicate);

        Predicate andPredicate = queryBuilder
                .and(bornInMarchPredicate, carYearAfter2020Predicate);

        criQueryPerson.where(andPredicate);

        TypedQuery<Driver> driverQuery = em.createQuery(criQueryPerson);
        List<Driver> drivers = driverQuery.getResultList();
        System.out.println("drivers Query ResultList: ");


        drivers.forEach(driver->{
            System.out.println(driver);
        });
        System.out.println();
        System.out.println("--------------------------------------------------");
    }

    public static void populateDb(EntityManager em){
        // 01. Create Persons
        Owner ahmed = new Owner("Ahmed", LocalDate.of(1986, 3, 15));
        Owner mohamed = new Owner("Mohamed", LocalDate.of(1990, 4, 10));
        Owner ayman = new Owner("Ayman", LocalDate.of(1995, 5, 12));

        Driver abdurrahman = new Driver("Abdurrahman", LocalDate.of(1989, 2, 15));
        Driver amgad = new Driver("Amgad", LocalDate.of(1999, 3, 15));
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
        Car camry = new Car("Camry", 4_000, 2007, toyota);
        Car fusion = new Car("Fusion", 7_000, 2008, ford);
        Car sunny = new Car("Sunny", 5_000, 2012, nissan);
        Car civic = new Car("Civic", 70_000, 2022, honda);
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
        //civic.addOwner(ayman);

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
