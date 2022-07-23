package edu.miu.cs544.week04.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Owner.findOwnersByMileageAndAge",
        query = "SELECT DISTINCT o FROM Owner o JOIN o.cars c WHERE o.age>:age AND c.mileage >:mileage")
@DiscriminatorValue("Owner")
public class Owner extends Person {

    @ManyToMany(mappedBy = "owners")
    private List<Car> cars = new ArrayList<>();

    @Version
    private Long version;

    public Owner() {
        super();
    }

    public Owner(String name, LocalDate dob) {
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
