package edu.miu.cs544.week02.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Owner")
public class Owner extends Person {

    @ManyToMany(mappedBy = "owners")
    private List<Car> cars = new ArrayList<>();

    public Owner() {
    }

    public Owner(String name, String dob) {
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
