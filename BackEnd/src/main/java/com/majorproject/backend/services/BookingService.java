package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.*;
import com.majorproject.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private EmployeeScheduleRepository employeeScehduleRepository;
    @Autowired
    private CustomerRepository customerRepository;


    public Booking saveOrUpdateBooking(Booking booking) {
        try {
            Customer customer = customerRepository.findById(booking.getCustomer().getId()).get();
            booking.setCustomer(customer);

            EmployeeSchedule employeeSchedule = employeeScehduleRepository.findById(booking.getEmployeeSchedule().getId()).get();
            booking.setEmployeeSchedule(employeeSchedule);
        } catch (Exception e) {
            throw new ResponseException(HttpStatus.NOT_ACCEPTABLE, "Customer, employee or service does not exist");
        }
        return bookingRepository.save(booking);
    }

    public List<Booking> displayDashboard(String id, String userType) {
        List<Booking> bookingList = null;


        return bookingList;
    }
}
