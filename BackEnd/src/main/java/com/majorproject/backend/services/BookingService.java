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
    @Autowired
    private EmployeeRepository employeeRepository;


    public Booking saveOrUpdateBooking(Booking booking) {
        try {
            Customer customer = customerRepository.findById(booking.getCustomer().getId()).get();
            booking.setCustomer(customer);

            EmployeeSchedule employeeSchedule = employeeScehduleRepository.findById(booking.getEmployeeSchedule().getId()).get();
            booking.setEmployeeSchedule(employeeSchedule);
            employeeSchedule.setAvailability(false);

        } catch (Exception e) {
            throw new ResponseException(HttpStatus.NOT_ACCEPTABLE, "Customer, employee or service does not exist");
        }
        return bookingRepository.save(booking);
    }

//    public List<Booking> displayDashboard(String id, String userType) {
//        List<Booking> bookingList = null;
//        return bookingList;
//    }

    public List<Booking> getBookingsByUser(String username) {
        List<Booking> bookingList = null;
        Employee employee = employeeRepository.findByUsername(username);

        if (employee == null) {
            Customer customer = customerRepository.findByUsername(username);
            bookingList = bookingRepository.getAllCustomerBookings(customer.getId());
        } else {
            bookingList = bookingRepository.getAllEmployeeBookings(employee.getId());
        }

        if(bookingList == null)
            throw new ResponseException(HttpStatus.NO_CONTENT, "There are no bookings.");


        return bookingList;
    }
}
