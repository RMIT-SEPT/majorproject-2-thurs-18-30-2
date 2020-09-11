package com.majorproject.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @NotNull(message = "Employee Id required")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @NotNull(message = "Customer Id required")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    @NotNull(message = "Service Id required")
    private Services service;

    @Basic
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @NotNull(message = "Date required")
    private Date date;

    @Basic
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
//    @NotNull(message = "Start time required")
    private Date startTime;

    @Basic
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
//    @NotNull(message = "End time required")
    private Date endTime;

    @JsonFormat(pattern ="yyyy-mm-dd")
    private Date createdAt;
    @JsonFormat(pattern ="yyyy-mm-dd")
    private Date updatedAt;

    public Booking() {

    }

    public Booking(Employee employee, Customer customer, Services service) {
        this.employee = employee;
        this.customer = customer;
        this.service = service;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}