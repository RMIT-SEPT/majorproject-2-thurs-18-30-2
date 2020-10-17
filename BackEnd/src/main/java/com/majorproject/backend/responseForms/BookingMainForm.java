package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is used in BookingService to create a custom list
 */
public class BookingMainForm {
    // From Booking
    private long bookingId;
    private String bookingCreatedAt;

    // From Customer
    private long customerId;
    private String customerFName;
    private String customerLName;
    private String customerEmail;

    // From Employee Schedule
    private long employeeScheduleId;
    private Date scheduleDate;
    private Date scheduleStartTime;
    private Date scheduleEndTime;

    // From Employee
    private long employeeId;
    private String employeeFName;
    private String employeeLName;
    private String employeeEmail;

    // From BService
    private long bServiceId;
    private String bServiceName;
    private String bServiceDescription;

    public BookingMainForm(Booking booking) {
        Customer customer = booking.getCustomer();
        EmployeeSchedule employeeSchedule = booking.getEmployeeSchedule();
        Employee employee = employeeSchedule.getEmployee();
        BService bService = employeeSchedule.getBService();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Booking
        this.bookingId = booking.getId();
        this.bookingCreatedAt = sdf.format(booking.getCreatedAt());

        // Customer
        this.customerId = customer.getId();
        this.customerFName = customer.getfName();
        this.customerLName = customer.getlName();
        this.customerEmail = customer.getEmail();

        // Employee Schedule
        this.employeeScheduleId = employeeSchedule.getId();
        this.scheduleDate = employeeSchedule.getDate();
        this.scheduleStartTime = employeeSchedule.getStartTime();
        this.scheduleEndTime = employeeSchedule.getEndTime();

        // Employee
        this.employeeId = employee.getId();
        this.employeeFName = employee.getfName();
        this.employeeLName = employee.getlName();
        this.employeeEmail = employee.getEmail();

        // BService
        this.bServiceId = bService.getId();
        this.bServiceName = bService.getName();
        this.bServiceDescription = bService.getDescription();
    }

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingCreatedAt() {
        return bookingCreatedAt;
    }

    public void setBookingCreatedAt(String bookingCreatedAt) {
        this.bookingCreatedAt = bookingCreatedAt;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFName() {
        return customerFName;
    }

    public void setCustomerFName(String customerFName) {
        this.customerFName = customerFName;
    }

    public String getCustomerLName() {
        return customerLName;
    }

    public void setCustomerLName(String customerLName) {
        this.customerLName = customerLName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public long getEmployeeScheduleId() {
        return employeeScheduleId;
    }

    public void setEmployeeScheduleId(long employeeScheduleId) {
        this.employeeScheduleId = employeeScheduleId;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Date getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(Date scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public Date getScheduleEndTime() {
        return scheduleEndTime;
    }

    public void setScheduleEndTime(Date scheduleEndTime) {
        this.scheduleEndTime = scheduleEndTime;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFName() {
        return employeeFName;
    }

    public void setEmployeeFName(String employeeFName) {
        this.employeeFName = employeeFName;
    }

    public String getEmployeeLName() {
        return employeeLName;
    }

    public void setEmployeeLName(String employeeLName) {
        this.employeeLName = employeeLName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public long getbServiceId() {
        return bServiceId;
    }

    public void setbServiceId(long bServiceId) {
        this.bServiceId = bServiceId;
    }

    public String getbServiceName() {
        return bServiceName;
    }

    public void setbServiceName(String bServiceName) {
        this.bServiceName = bServiceName;
    }

    public String getbServiceDescription() {
        return bServiceDescription;
    }

    public void setbServiceDescription(String bServiceDescription) {
        this.bServiceDescription = bServiceDescription;
    }
}
