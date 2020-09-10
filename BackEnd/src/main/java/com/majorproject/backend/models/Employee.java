package com.majorproject.backend.models;

import javax.persistence.*;

@Entity
public class Employee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;
    private String empType;

    public Employee() {

    }

    public Employee(String fName, String lName, String email, String username,
                    String password, String address, String pNumber, String empType) {
        super(fName, lName, email, username, password, address, pNumber);
        this.empType = empType;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEmpType() { return empType; }

    public void setEmpType(String empType) { this.empType = empType; }
}