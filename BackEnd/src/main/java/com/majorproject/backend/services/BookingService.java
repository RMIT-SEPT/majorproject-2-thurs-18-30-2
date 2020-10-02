package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.*;
import com.majorproject.backend.repositories.*;
import com.majorproject.backend.responseForms.BookingMainForm;
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

    private IdErrorService idErrorService = new IdErrorService();
    private ListEmptyErrorService listEmptyErrorService = new ListEmptyErrorService();

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
        List<BookingMainForm> bookingMainFormList = new ArrayList<BookingMainForm>();

        listEmptyErrorService.checkListEmpty(bookingList, "Booking");

        for(int i = 0; i < bookingList.size(); ++i) {
            bookingMainFormList.add(new BookingMainForm(bookingList.get(i)));
        }

        return bookingMainFormList;
    }

    public List<BookingMainForm> getBookingsForUserById(String userTypeAPI, String idAPI) {
        List<Booking> bookingList = new ArrayList<Booking>();
        List<BookingMainForm> bookingMainFormList = new ArrayList<BookingMainForm>();

        Long userId = idErrorService.idStringToLong(idAPI);

        if (userTypeAPI.equals("customer")) { // if user is a customer
            bookingList = bookingRepository.getAllCustomerBookings(userId);
        } else { // if user is an employee
            bookingList = bookingRepository.getAllEmployeeBookings(userId);
        }

        listEmptyErrorService.checkListEmpty(bookingList, "Booking");

        for(int i = 0; i < bookingList.size(); ++i) {
            bookingMainFormList.add(new BookingMainForm(bookingList.get(i)));
        }

        return bookingMainFormList;
    }
}
