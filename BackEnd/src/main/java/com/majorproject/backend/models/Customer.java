package com.majorproject.backend.models;

import javax.persistence.*;

@Entity
public class Customer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    public Customer(String fName, String lName, String email, String password,
                    String address, String pNumber) {
        super(fName, lName, email, password, address, pNumber);
    }

    public Customer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}