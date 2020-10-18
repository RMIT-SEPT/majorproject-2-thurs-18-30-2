package com.majorproject.backend.models;

import javax.persistence.*;

/**
 * Employee
 */
@Entity
public class Employee extends User {
    private String empType;

    public Employee() {

    }

    public Employee(String fName, String lName, String email, String username,
                    String password, String address, String pNumber, String empType) {
        super(fName, lName, email, username, password, address, pNumber);
        this.empType = empType;
    }

    public String getEmpType() { return empType; }

    public void setEmpType(String empType) { this.empType = empType; }
}