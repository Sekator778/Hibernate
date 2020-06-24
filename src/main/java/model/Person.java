package model;

import javax.persistence.*;
import java.util.List;

/**
 *
 */
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "person")
    private List<HomeAddress> address;

    public Person(String name, List<HomeAddress> homeAddress) {
        this.name = name;
        this.address = homeAddress;
    }

    public Person() {
    }
}
