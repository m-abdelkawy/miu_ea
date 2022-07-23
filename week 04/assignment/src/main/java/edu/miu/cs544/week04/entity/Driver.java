package edu.miu.cs544.week04.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
//@DiscriminatorValue("Driver")
public class Driver extends Person{

    @Version
    private Long version;

    @ManyToMany(mappedBy = "drivers")
    private List<Car> cars = new ArrayList<>();

    public Driver() {
        super();
    }

    public Driver(String name, LocalDate dob) {
        super(name, dob);
    }

    public boolean addCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("provided car cannot be null");
        }
        return cars.add(car);
    }

    public boolean removeCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("provided car cannot be null");
        }
        return cars.remove(car);
    }
}
