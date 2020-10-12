package com.majorproject.backend.models;

import javax.persistence.*;
import java.util.Collection;

/**
 * Customer
 */
@Entity
public class Customer extends User {

    public Customer() {

    }

    public Customer(String fName, String lName, String email, String username,
                    String password, String address, String pNumber) {
        super(fName, lName, email, username, password, address, pNumber);
    }
}