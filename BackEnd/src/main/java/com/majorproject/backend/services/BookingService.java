package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.*;
import com.majorproject.backend.repositories.*;
import com.majorproject.backend.responseForms.BookingMainForm;
import com.majorproject.backend.util.DateErrorUtil;
import com.majorproject.backend.util.DateNowUtil;
import com.majorproject.backend.util.IdErrorUtil;
import com.majorproject.backend.util.ListEmptyErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    private IdErrorUtil idErrorUtil = new IdErrorUtil();
    private ListEmptyErrorUtil listEmptyErrorUtil = new ListEmptyErrorUtil();
    private DateErrorUtil dateErrorUtil = new DateErrorUtil();
    private DateNowUtil dateNowUtil = new DateNowUtil();

    /**
     * Creates the booking
     * @param booking The booking
     * @return The booking
     */
    public Booking saveOrUpdateBooking(Booking booking) {
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
                // Update availability = false
                employeeScheduleRepository.updateEmployeeScheduleAfterBooked(employeeScheduleId);
            }

        return bookingRepository.save(booking);
    }

    /**
     * Gets a list of bookings based on the state (either past or current)
     * @param state The state of bookings specified by the user either past or current
     * @return A custom list of bookings
     */
    public List<BookingMainForm> getAllBookings(String state) {

        List<Booking> bookingList;
        if(state.equals("past")) bookingList = bookingRepository.getAllBookingsBefore(new Date());
        else bookingList = bookingRepository.getAllBookingsAfter(new Date());
        List<BookingMainForm> bookingMainFormList = new ArrayList<BookingMainForm>();

        listEmptyErrorUtil.checkListEmpty(bookingList, "Booking");

        for(int i = 0; i < bookingList.size(); ++i) {
            bookingMainFormList.add(new BookingMainForm(bookingList.get(i)));
        }

        return bookingMainFormList;
    }

    /**
     * Gets a list of bookings based on the user type, id and state (either past or current)
     * @param userTypeAPI The user type
     * @param idAPI The user id
     * @param state The state of bookings specified by user, either past or current
     * @return A custom list of bookings based on the above
     */
    public List<BookingMainForm> getBookingsForUserById(String userTypeAPI, String idAPI, String state) {
        List<Booking> bookingList = new ArrayList<Booking>();
        List<BookingMainForm> bookingMainFormList = new ArrayList<BookingMainForm>();

        Long userId = idErrorUtil.idStringToLong(idAPI);

        if (userTypeAPI.equals("customer")) { // if user is a customer
            if(state.equals("past")) bookingList = bookingRepository.getAllCustomerBookingsBefore(userId, new Date());
            else bookingList = bookingRepository.getAllCustomerBookingsAfter(userId, new Date());
        } else { // if user is an employee
            if(state.equals("past")) bookingList = bookingRepository.getAllEmployeeBookingsBefore(userId, new Date());
            else bookingList = bookingRepository.getAllEmployeeBookingsAfter(userId, new Date());
        }
        listEmptyErrorUtil.checkListEmpty(bookingList, "Booking");

        for(int i = 0; i < bookingList.size(); ++i) {
            bookingMainFormList.add(new BookingMainForm(bookingList.get(i)));
        }

        return bookingMainFormList;
    }

    /**
     * Deletes a booking based on the booking id
     * @param bookingIdAPI The booking id
     * @return A string that says the booking is deleted, if successful
     */
    public String deleteBooking(String bookingIdAPI) {
        boolean toBeDeleted = false;
        String success = "Booking is successfully deleted";

        long bookingId = idErrorUtil.idStringToLong(bookingIdAPI);
        Booking booking = bookingRepository.getBookingById(bookingId);

        if(booking != null) {
            Date bookingDate = booking.getEmployeeSchedule().getDate();
            Date bookingTime = booking.getEmployeeSchedule().getStartTime();
            Date currDate = dateNowUtil.getCurrentDate();
            Date currTime = dateNowUtil.getCurrentTime();

            // Check if date time is within 48 hours, otherwise cannot delete
            // One day has 86,400,000 milliseconds
            long dayToMilliSec = 86400000;
            long dateDiff = (bookingDate.getTime() - currDate.getTime()) / dayToMilliSec;

            if(dateDiff > 2) { // If the date difference is 2 or more days
                toBeDeleted = true;
            } else if(dateDiff == 2) { // If the data difference is 2 days
                if(currTime.before(bookingTime)) {
                    toBeDeleted = true;
                } else {
                    throw new ResponseException(HttpStatus.BAD_REQUEST, "Late request. Please contact business owner directly asap. Thank you");
                }
            } else {
                throw new ResponseException(HttpStatus.BAD_REQUEST, "Late request. Please contact business owner directly asap. Thank you");
            }
        } else {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Booking does not exist");
        }

        // Now its time to delete the booking and set the employee schedule availability to true
        if(toBeDeleted) {
            long employeeScheduleId = booking.getEmployeeSchedule().getId();
            bookingRepository.deleteById(bookingId);
            employeeScheduleRepository.updateEmployeeScheduleAfterBookingDeleted(employeeScheduleId);
        }

        return success;
    }
}
