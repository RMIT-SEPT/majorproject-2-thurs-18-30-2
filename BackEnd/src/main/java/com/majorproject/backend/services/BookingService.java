package com.majorproject.backend.services;

import com.majorproject.backend.models.Booking;
import com.majorproject.backend.repositories.BookingRepository;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;


    public Booking saveOrUpdateBooking(Booking booking) {

        return bookingRepository.save(booking);
    }

    public List<Booking> displayDashboard(String id, String userType) {
        List<Booking> bookingList = null;


        return bookingList;
    }
}
