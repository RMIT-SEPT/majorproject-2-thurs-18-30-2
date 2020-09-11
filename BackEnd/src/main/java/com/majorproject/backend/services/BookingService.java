package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.Booking;
import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.Services;
import com.majorproject.backend.repositories.BookingRepository;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.repositories.ServiceRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private ServiceRepository serviceRepository;


    public Booking saveOrUpdateBooking(Booking booking) {
        try {
            Customer customer = customerRepository.findById(booking.getCustomer().getId()).get();
            booking.setCustomer(customer);

            Employee employee = employeeRepository.findById(booking.getEmployee().getId()).get();
            booking.setEmployee(employee);

            Services services = serviceRepository.findById(booking.getService().getId()).get();
            booking.setService(services);
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
