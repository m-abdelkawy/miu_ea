package edu.miu.cs544.week05;

import edu.miu.cs544.week05.entity.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start...");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("car_pu05");
        EntityManager em = emf.createEntityManager();

        //populateDb(em);

        //runQueries(em);

        //week4(em);

        week5(em);

        em.close();
        emf.close();

        System.out.println("End...");
    }

    static void runQueries(EntityManager em) {
        // 01. Write a query to find all Cars in your program using JPQL
        System.out.println("01. Write a query to find all Cars in your program using JPQL");

        TypedQuery<Car> queryFindAllCars = em.createQuery("SELECT c FROM Car c", Car.class);
        List<Car> allCars = queryFindAllCars.getResultList();

        allCars.forEach(c -> {
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
        owners.forEach(o -> {
            System.out.println(o);
        });
        System.out.println();
        System.out.println("--------------------------------------------------");

        // 03. Write a Criteria API query to return all Car Insurance Companies
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<InsuranceCompany> criQuery = criteriaBuilder.createQuery(InsuranceCompany.class);
        Root<InsuranceCompany> root = criQuery.from(InsuranceCompany.class);
        criQuery.select(root);
        TypedQuery<InsuranceCompany> insCriQuery = em.createQuery(criQuery);
        List<InsuranceCompany> insComps = insCriQuery.getResultList();
        System.out.println("Insurance Company Query ResultList: ");
        insComps.forEach(i -> {
            System.out.println(i);
        });
        System.out.println();
        System.out.println("--------------------------------------------------");

        // 04. Write a JPQL to return all Companies.
        TypedQuery<Company> findAllCompanies = em.createQuery("SELECT c FROM Company c", Company.class);
        List<Company> comps = findAllCompanies.getResultList();
        System.out.println("all Companies Query ResultList: ");
        comps.forEach(comp -> {
            System.out.println(comp);
        });
        System.out.println();
        System.out.println("--------------------------------------------------");

        // 05. Write a Criteria API query to return all People younger than 25
        // and drive a Car made after 2020 the Car is manufactured
        // in a company with more than 1000 employees, and the Car is insured with a company of rating 3 or higher.

        CriteriaQuery<Car> criQueryCar = criteriaBuilder.createQuery(Car.class);
        Root<Car> rootCar = criQueryCar.from(Car.class);
        // 01. car made after 2020
        Predicate carMadeAfter2020 = criteriaBuilder.greaterThan(rootCar.get("year"), 2020);

        // 02. car made in a company with noOfEmployees > 1000
        Join<Car, CarManufacturer> joinManufacturer = rootCar.join("carManufacturer");
        Predicate mfrNoOfEmployeeGt1000 = criteriaBuilder.greaterThan(joinManufacturer.get("numberOfEmployees"), 1000);

        // 03. car is insured with a company of rating of 3 or higher
        Join<Car, InsuranceCompany> joinInsuranceCompany = rootCar.join("insuranceCompany");
        Predicate insRated3Plus = criteriaBuilder.greaterThanOrEqualTo(joinInsuranceCompany.get("rating"), 3);

        System.out.println("05-1. Cars Query ResultList: ");

        Predicate andPredicateCars = criteriaBuilder.and(carMadeAfter2020, mfrNoOfEmployeeGt1000, insRated3Plus);
        criQueryCar.where(andPredicateCars);

        TypedQuery<Car> carQuery = em.createQuery(criQueryCar);
        List<Car> cars = carQuery.getResultList();
        cars.forEach(System.out::println);

        System.out.println();
        System.out.println("--------------------------------------------------");

        CriteriaQuery<Driver> criteriaQueryPerson = criteriaBuilder.createQuery(Driver.class);
        Root<Driver> rootDriver = criteriaQueryPerson.from(Driver.class);

        // 01. drivers younger than 25
        Predicate driversYoungerThan25 = criteriaBuilder.lessThan(rootDriver.get("age"), 25);

        // 02. join with cars made after 2020
        Join<Driver, Car> joinCar = rootDriver.join("cars");
        Predicate carsMadeAfter2020 = criteriaBuilder.greaterThan(joinCar.get("year"), 2020);

        // 03. car manufactured by a company with numberOfEmployees > 1000
        Join<Join<Driver, Car>, CarManufacturer> joinDriverCarManufacturer = joinCar.join("carManufacturer");
        Predicate manufacturerHasMoreThan1000Emplyees =
                criteriaBuilder.greaterThan(joinDriverCarManufacturer.get("numberOfEmployees"), 1000);

        // 04. car insured by insurance company rated 3 or higher
        Join<Join<Driver, Car>, InsuranceCompany> joinDriverCarInsurance = joinCar.join("insuranceCompany");
        Predicate insCopmanyRated3Plus =
                criteriaBuilder.greaterThanOrEqualTo(joinDriverCarInsurance.get("rating"), 3);

        // and predicate
        Predicate andPredicatePerson = criteriaBuilder
                .and(driversYoungerThan25, carsMadeAfter2020, manufacturerHasMoreThan1000Emplyees, insCopmanyRated3Plus);

        criteriaQueryPerson.where(andPredicatePerson);

        TypedQuery<Driver> driverTypedQuery = em.createQuery(criteriaQueryPerson);

        List<Driver> drivers = driverTypedQuery.getResultList();

        System.out.println("05-2 drivers query result");
        drivers.forEach(System.out::println);
        System.out.println();
        System.out.println("--------------------------------------------------");
    }

    static void populateDb(EntityManager em) {
        // 01. Create Persons
        Owner ahmed = new Owner("Ahmed", LocalDate.of(1986, 3, 15));
        Owner mohamed = new Owner("Mohamed", LocalDate.of(1990, 4, 10));
        Owner ayman = new Owner("Ayman", LocalDate.of(1995, 5, 12));
        Owner jack = new Owner("Jack", LocalDate.of(1991, 5, 12));

        Driver abdurrahman = new Driver("Abdurrahman", LocalDate.of(1989, 2, 15));
        Driver amgad = new Driver("Amgad", LocalDate.of(1999, 3, 15));
        Driver salem = new Driver("Salem", LocalDate.of(2000, 2, 15));

        // 02. Create Manufacturers
        Address toyotaAddress = new Address("1", "Toyota Rd", "Plano", "TX", "75024");
        Address fordAddress = new Address("1", "American Rd", "Dearborn", "MI", "48126");
        Address nissanAddress = new Address("1", "Nissan Way", "Franklin", "TN", "37067");
        Address hondaAddress = new Address("1", "Torrance Blvd", "Torrance", "CA", "90501");

        CarManufacturer toyota = new CarManufacturer("Toyota", toyotaAddress, 250000, 1947);
        CarManufacturer ford = new CarManufacturer("Ford", fordAddress, 200000, 1950);
        CarManufacturer nissan = new CarManufacturer("Nissan", nissanAddress, 150000, 1958);
        CarManufacturer honda = new CarManufacturer("Honda", hondaAddress, 230_1000, 1945);

        // 02. Create Insurance companies
        Address statefarmAddress = new Address("400", "Flowers St", "Bloomington", "IL", "128934");
        Address progressivAddress = new Address("507", "Desert Rd", "Los Angeles", "CA", "90189");

        InsuranceCompany stateFarm = new InsuranceCompany("State Farm", statefarmAddress, 4);
        InsuranceCompany progressive = new InsuranceCompany("Progressive", progressivAddress, 2);

        // 03. Create Cars
        Car avalon = new Car("Avalon", 15000, 2012, toyota);
        Car camry = new Car("Camry", 4_000, 2007, toyota);
        Car fusion = new Car("Fusion", 7_000, 2008, ford);
        Car sunny = new Car("Sunny", 5_000, 2012, nissan);
        Car civic = new Car("Civic", 70_000, 2022, honda);
        Car accord = new Car("Accord", 80_000, 2015, honda);


        Car c1 = new Car("c1", 80_000, 2015, honda);
        Car c2 = new Car("c2", 80_000, 2015, nissan);
        Car c3 = new Car("c3", 80_000, 2015, ford);

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

        c1.addOwner(jack);
        c2.addOwner(jack);
        c3.addOwner(jack);

        // 06. Assign Drivers
        avalon.addDriver(abdurrahman);
        avalon.addDriver(salem);
        civic.addDriver(amgad);
        civic.addDriver(abdurrahman);
        accord.addDriver(salem);

        jack.addCar(c1);
        jack.addCar(c2);
        jack.addCar(c3);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Persist cars
        em.persist(avalon);
        em.persist(camry);
        em.persist(fusion);
        em.persist(sunny);
        em.persist(civic);
        em.persist(accord);

        em.persist(c1);
        em.persist(c2);
        em.persist(c3);

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

        em.persist(jack);


        tx.commit();
    }

    static void week4(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 04-02 The Insurance company could be modified
        // by other applications that are not JPA. Make sure your application can work with that.
        System.out.println("04-02-----");
        Map props = new HashMap();
        props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        props.put("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);

        InsuranceCompany stateFarm =
                em.find(InsuranceCompany.class, 11L, LockModeType.PESSIMISTIC_FORCE_INCREMENT, props);
        System.out.println("Before update:");
        System.out.println(stateFarm);
        //System.out.println(stateFarm.getVersion());
        stateFarm.setRating(4);
        em.persist(stateFarm);
        System.out.println("After update:");
        System.out.println(stateFarm);
        //System.out.println(stateFarm.getVersion());

        System.out.println();
        System.out.println("--------------------------------------------------");

        // 04-03 Create a query that updates the DOB of a Person.
        // This query is executed by several users of our application at the same time.
        // This could result in issues, so make this query safe.
        // The chances of collision when using this query are very high.
        System.out.println("04-03-----");

        // for checking update
        Person personFromDb = em.find(Owner.class, 13L);
        System.out.println("Before update:");
        System.out.println(personFromDb);

        // query
        TypedQuery<Owner> selectPersonQuery =
                em.createQuery("SELECT p FROM Person p WHERE p.id = :id", Owner.class);
        selectPersonQuery.setParameter("id", 13L);

        selectPersonQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);

        selectPersonQuery.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        selectPersonQuery.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);

        Owner owner = selectPersonQuery.getSingleResult();

        owner.setDob(LocalDate.of(1991, 10, 20));


        System.out.println("After update:");
        System.out.println(personFromDb);

        /*Person personFromDb = em.find(Owner.class, 13L);
        System.out.println("Before update:");
        System.out.println(personFromDb);

        em.lock(personFromDb, LockModeType.PESSIMISTIC_WRITE);
        personFromDb.setDob(LocalDate.of(1989, 10, 20));
        em.persist(personFromDb);

        System.out.println("After update:");
        System.out.println(personFromDb);*/

        tx.commit();

        System.out.println();
        System.out.println("--------------------------------------------------");
    }

    static void week5(EntityManager em) {
        // 1- Log to the console every time a Car is added to the database.
        populateDb(em);

        // 2- Log to the console every time a Person is deleted from the database.
        deletePersons(em);

        // 3-
        //Owner jack = findOwnerByName(em, "Jack");
        em.getTransaction().begin();
        Owner jack = em.find(Owner.class, 22L);
        System.out.println(jack);
        em.getTransaction().commit();
        em.detach(jack);

        List<Car> jackCars = findOwnerCars(jack);

        System.out.println("Jack's Cars");
        jackCars.forEach(System.out::println);
    }

    static Owner findOwnerByName(EntityManager em, String name) {
        TypedQuery<Owner> findOwnerByName = em.createNamedQuery("Owner.findOwnerByName", Owner.class);
        findOwnerByName.setParameter("name", name);

        return findOwnerByName.getSingleResult();
    }

    static List<Car> findOwnerCars(Owner owner){
        return owner.getCars();
    }

    static void deletePersons(EntityManager em) {
        //owners 13, 14, 15
        //drivers 16, 17, 18

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Person abdurrahman = em.find(Person.class, 19L);
        if (abdurrahman != null)
            em.remove(abdurrahman);

        Person ayman = em.find(Owner.class, 18L);
        if (ayman != null)
            em.remove(ayman);
        tx.commit();
    }
}
