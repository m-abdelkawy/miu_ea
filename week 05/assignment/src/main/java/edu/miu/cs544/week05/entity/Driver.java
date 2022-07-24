package edu.miu.cs544.week05.entity;

import edu.miu.cs544.week05.entity.listeners.PersonDeleteListener;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners({
        PersonDeleteListener.class
})
//@DiscriminatorValue("Driver")
public class Driver extends Person{

    @Version
    private Long version;

    @ManyToMany(mappedBy = "drivers", fetch = FetchType.LAZY)
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
