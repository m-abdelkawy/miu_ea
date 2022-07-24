package edu.miu.cs544.week05.entity;

import edu.miu.cs544.week05.entity.listeners.CarPersistenceListener;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@EntityListeners({CarPersistenceListener.class})
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String model;
    private int mileage;
    private int year;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "MFR_COMP_ID", referencedColumnName = "ID", nullable = false)
    private CarManufacturer carManufacturer;

    @ManyToOne
    @JoinColumn(name = "Ins_COMP_ID", referencedColumnName = "ID", nullable = true)
    private InsuranceCompany insuranceCompany;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CAR_OWNER",
            joinColumns = @JoinColumn(name = "CAR_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "OWNER_ID", referencedColumnName = "ID"))
    private List<Person> owners;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CAR_DRIVER",
            joinColumns = @JoinColumn(name = "CAR_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "DRIVER_ID", referencedColumnName = "ID"))
    private List<Person> drivers;

    public Car() {
    }

    public Car(String model, int mileage, int year, CarManufacturer carManufacturer) {
        this.model = model;
        this.mileage = mileage;
        this.year = year;
        this.carManufacturer = carManufacturer;
    }

    public Car(String model, int mileage, int year, CarManufacturer carManufacturer, InsuranceCompany insuranceCompany) {
        this.model = model;
        this.mileage = mileage;
        this.year = year;
        this.carManufacturer = carManufacturer;
        this.insuranceCompany = insuranceCompany;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Company getCarManufacturer() {
        return carManufacturer;
    }

    public void setCarManufacturer(CarManufacturer carManufacturer) {
        this.carManufacturer = carManufacturer;
    }

    public Company getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(InsuranceCompany insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public List<Person> getOwners() {
        return Collections.unmodifiableList(owners);
    }

    public void addOwner(Person owner) {
        if (owner == null)
            throw new IllegalArgumentException("Owner cannot be null");
        if (owners == null)
            this.owners = new ArrayList<>();
        if (this.owners.size() >= 2)
            throw new IllegalArgumentException("car owners cannot exceed 2!");

        this.owners.add(owner);
    }

    public List<Person> getDrivers() {
        return Collections.unmodifiableList(drivers);
    }

    public void addDriver(Person driver) {
        if (driver == null)
            throw new IllegalArgumentException("driver cannot be null!");
        if (drivers == null)
            drivers = new ArrayList<>();
        this.drivers.add(driver);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", mileage=" + mileage +
                ", year=" + year +
                ", carManufacturer=" + carManufacturer.getName() +
                ", insuranceCompany=" + (insuranceCompany == null ? "N/A" : insuranceCompany.getName()) +
                '}';
    }
}
