package edu.miu.cs544.week05.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
//@DiscriminatorValue("Mfr")
public class CarManufacturer extends Company{
    private int numberOfEmployees;
    private int yearEstablished;

    @Version
    private Long version;

    @OneToMany(mappedBy = "carManufacturer")
    private List<Car> cars=new ArrayList<>();

    public CarManufacturer() {
        super();
    }

    public CarManufacturer(String name, Address address) {
        super(name, address);
    }

    public CarManufacturer(String name, Address address, int numberOfEmployees, int yearEstablished) {
        super(name, address);
        this.numberOfEmployees = numberOfEmployees;
        this.yearEstablished = yearEstablished;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public int getYearEstablished() {
        return yearEstablished;
    }

    public void setYearEstablished(int yearEstablished) {
        this.yearEstablished = yearEstablished;
    }

    @Override
    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    @Override
    public void addCar(Car car) {
        if(car == null)
            throw new IllegalArgumentException("car cannot be null!");
        this.cars.add(car);
    }

    @Override
    public String toString() {
        return "CarManufacturer{" +
                "Id=" + this.getId()+
                ", name=" + getName()+
                ", numberOfEmployees=" + numberOfEmployees +
                ", yearEstablished=" + yearEstablished +
                '}';
    }
}
