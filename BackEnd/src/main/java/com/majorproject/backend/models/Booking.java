package com.majorproject.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.majorproject.backend.dashboard.BookingStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Booking(Employee employee, Customer customer, @NotBlank(message = "Service required") String service) {
        this.employee = employee;
        this.customer = customer;
        this.service = service;
    }

    public Booking() {

    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotBlank(message = "Service required")
    private String service;

    @JsonFormat(pattern ="yyyy-mm-dd")
    private Date createdAt;
    @JsonFormat(pattern ="yyyy-mm-dd")
    private Date updatedAt;

    private BookingStatus bookingStatus;

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

    public String getService() {
        return service;
    }

    public void setService(String service) {
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

    public BookingStatus getBookingStatus() { return bookingStatus; }

    public void setBookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus; }
}