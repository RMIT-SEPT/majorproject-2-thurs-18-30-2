package com.majorproject.backend.services;

import com.majorproject.backend.jsonconv.BookingForm;
import com.majorproject.backend.models.Booking;
import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;


    public Booking saveOrUpdateBooking(BookingForm bookingForm) {
        Employee employee = employeeService.getEmployeeByEmail(bookingForm.getEmployeeEmail());
        Customer customer = customerService.getCustomerByEmail(bookingForm.getEmployeeEmail());
        Booking booking = new Booking(employee, customer, bookingForm.getService());
        //logic
        return bookingRepository.save(booking);
    }
}
