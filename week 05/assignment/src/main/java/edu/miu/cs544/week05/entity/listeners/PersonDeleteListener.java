package edu.miu.cs544.week05.entity.listeners;

import edu.miu.cs544.week05.entity.Person;
import jakarta.persistence.PostRemove;

public class PersonDeleteListener {
    @PostRemove
    private void logDeletedPerson(Person person){
        System.out.println("inside 'logDeletedPerson':");
        System.out.println(person + "    --deleted");
        System.out.println("-----------_____________________-----------");
    }
}
