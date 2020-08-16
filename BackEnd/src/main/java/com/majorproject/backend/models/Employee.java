package com.majorproject.backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class Employee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String empType;

    public Employee(String fName, String lName, String email, String password,
                    String address, String pNumber, String empType) {
        super(fName, lName, email, password, address, pNumber);
        this.empType = empType;
    }

    public Employee() {

    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEmpType() { return empType; }

    public void setEmpType(String empType) { this.empType = empType; }
}