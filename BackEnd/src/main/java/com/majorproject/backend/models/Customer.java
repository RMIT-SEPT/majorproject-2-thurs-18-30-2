package com.majorproject.backend.models;

import javax.persistence.*;

@Entity
public class Customer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    public Customer() {

    }

    public Customer(String fName, String lName, String email, String username,
                    String password, String address, String pNumber, String empType) {
        super(fName, lName, email, username, password, address, pNumber);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}