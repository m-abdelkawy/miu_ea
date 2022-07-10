package edu.miu.cs544.week02.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@DiscriminatorValue("ins")
public class InsuranceCompany extends Company {
    private int rating;

    @OneToMany(mappedBy = "insuranceCompany")
    private List<Car> cars = new ArrayList<>();

    public InsuranceCompany() {
        super();
    }

    public InsuranceCompany(String name, Address address) {
        super(name, address);
    }

    public InsuranceCompany(String name, Address address, int rating) {
        super(name, address);
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
        return "InsuranceCompany{" +
                "rating=" + rating +
                '}';
    }
}
