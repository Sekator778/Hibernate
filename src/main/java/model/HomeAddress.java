package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 */
@Entity
public class HomeAddress {
    @Id
    @GeneratedValue
    private int id;
    private String street;
    @ManyToOne
    Person person;

    public HomeAddress(String street) {
        this.street = street;
    }

    public HomeAddress() {
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
