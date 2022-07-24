package edu.miu.cs544.week05.entity.listeners;

import edu.miu.cs544.week05.entity.Car;
import jakarta.persistence.PostPersist;

public class CarPersistenceListener {
    @PostPersist
    private void logPersistedToConsole(Car car){
        System.out.println("Inside 'logPersistedToConsole':");
        System.out.println(car);
        System.out.println("---____----___---");
    }
}
