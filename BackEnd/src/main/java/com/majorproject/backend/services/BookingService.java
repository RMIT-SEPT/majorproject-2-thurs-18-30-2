package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.*;
import com.majorproject.backend.repositories.*;
import com.majorproject.backend.responseForms.BookingMainForm;
import com.majorproject.backend.responseForms.EmployeeAvailabilityForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * Creates the booking
     * @param booking The booking
     * @return The booking
     */
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

    public List<BookingMainForm> getAllBookings() {
        List<Booking> bookingList = bookingRepository.getAllBookings();

        // If there are no bookings
        if(bookingList.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "There are no bookings.");
        }

        List<BookingMainForm> bookingMainFormList = new ArrayList<BookingMainForm>();

        for(int i = 0; i < bookingList.size(); ++i) {
            bookingMainFormList.add(new BookingMainForm(bookingList.get(i)));
        }

        return bookingMainFormList;
    }

    public List<BookingMainForm> getBookingsForUserById(String userTypeAPI, String idAPI) {
        List<BookingMainForm> bookingMainFormList;
        Long id;

        try {
            id = Long.parseLong(idAPI);
        } catch(Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "ID error");
        }

        try {
            if (userTypeAPI.equals("customer")) { // if user is a customer
                bookingMainFormList = getBookingsForCustomerById(id);
            } else { // if user is an employee
                bookingMainFormList = getBookingsForEmployeeById(id);
            }
        } catch(Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "There are no bookings.");
        }

        return bookingMainFormList;
    }

    public List<BookingMainForm> getBookingsForCustomerById(Long customerId) {
        List<Booking> bookingList = null;
        List<BookingMainForm> bookingMainFormList = new ArrayList<BookingMainForm>();

        bookingList = bookingRepository.getAllCustomerBookings(customerId);

        if(bookingList.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "There are no bookings");
        }

        for(int i = 0; i < bookingList.size(); ++i) {
            bookingMainFormList.add(new BookingMainForm(bookingList.get(i)));
        }

        return bookingMainFormList;
    }

    public List<BookingMainForm> getBookingsForEmployeeById(Long employeeId) {
        List<Booking> bookingList = null;
        List<BookingMainForm> bookingMainFormList = new ArrayList<BookingMainForm>();

        bookingList = bookingRepository.getAllEmployeeBookings(employeeId);

        if(bookingList.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "There are no bookings");
        }

        for(int i = 0; i < bookingList.size(); ++i) {
                bookingMainFormList.add(new BookingMainForm(bookingList.get(i)));
        }

        return bookingMainFormList;
    }
}
