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
    private EmployeeScheduleRepository employeeScheduleRepository;
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
//        try {
            /**
             * Check if employee schedule is available
             * available = true then can book
             * available = false cannot book
             */

            long employeeScheduleId = booking.getEmployeeSchedule().getId();

            EmployeeSchedule employeeSchedule = employeeScheduleRepository.getEmployeeScheduleById(employeeScheduleId);

            if(!employeeSchedule.getAvailability()) {
                throw new ResponseException(HttpStatus.NOT_ACCEPTABLE, "Schedule has already been booked");
            } else {
//                Customer customer = customerRepository.findById(booking.getCustomer().getId()).get();
//                booking.setCustomer(customer);

                // Update availability = false
                employeeScheduleRepository.updateEmployeeScheduleAfterBooked(employeeScheduleId);
//                booking.setEmployeeSchedule(employeeSchedule);
            }

//            Customer customer = customerRepository.findById(booking.getCustomer().getId()).get();
//            booking.setCustomer(customer);
//
//            EmployeeSchedule employeeSchedule = employeeScheduleRepository.findById(booking.getEmployeeSchedule().getId()).get();
//            booking.setEmployeeSchedule(employeeSchedule);
//            employeeSchedule.setAvailability(false);
//            booking.getEmployeeSchedule().setAvailability(false);

//        } catch (Exception e) {
//            throw new ResponseException(HttpStatus.NOT_ACCEPTABLE, "Customer, employee or service does not exist");
//            throw new ResponseException(HttpStatus.NOT_ACCEPTABLE, "Booking error");
//        }

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
