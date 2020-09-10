package com.majorproject.backend.services;

import com.majorproject.backend.jsonconv.BookingForm;
import com.majorproject.backend.models.Booking;
import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.User;
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

    public Booking saveOrUpdateBooking(BookingForm bookingForm) {
//        Employee employee = employeeService.getEmployeeByEmail(bookingForm.getEmployeeEmail());
//        Customer customer = customerService.getCustomerByEmail(bookingForm.getEmployeeEmail());
        Employee employee = employeeRepository.findByEmail(bookingForm.getEmployeeEmail());
        Customer customer = customerRepository.findByEmail(bookingForm.getCustomerEmail());
        Booking booking = new Booking(employee, customer, bookingForm.getService());
        return bookingRepository.save(booking);
    }

    public List<Booking> displayDashboard(String username) {
        List<Booking> bookingList = null;
        User user = customerRepository.findByUsername(username);
        if(user != null) {
            bookingList = bookingRepository.refreshCustomerDashboard(username);
        } else {
            user = employeeRepository.findByUsername(username);
            bookingList = bookingRepository.refreshEmployeeDashboard(username);
        }

        return bookingList;
    }
}
