package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.*;
import com.majorproject.backend.repositories.*;
import com.majorproject.backend.responseForms.BookingMainForm;
import com.majorproject.backend.util.DateErrorService;
import com.majorproject.backend.util.DateNowUtil;
import com.majorproject.backend.util.IdErrorService;
import com.majorproject.backend.util.ListEmptyErrorService;
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

    private IdErrorService idErrorService = new IdErrorService();
    private ListEmptyErrorService listEmptyErrorService = new ListEmptyErrorService();
    private DateErrorService dateErrorService = new DateErrorService();
    private DateNowUtil dateNowUtil = new DateNowUtil();

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

    public String deleteBooking(String bookingIdAPI) {
        boolean toBeDeleted = false;
        String success = "Booking is successfully deleted";

        long bookingId = idErrorService.idStringToLong(bookingIdAPI);
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

            if(dateDiff > 2) {
                toBeDeleted = true;
            } else if(dateDiff == 2) {
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

//    public String deleteBooking(String bookingIdAPI, String dateAPI, String timeAPI) {
//        boolean toBeDeleted = false;
//        String success = "Booking is successfully deleted";
//
//        long bookingId = idErrorService.idStringToLong(bookingIdAPI);
//        Booking booking = bookingRepository.getBookingById(bookingId);
//
//        if(booking != null) {
//            Date bookingDate = booking.getEmployeeSchedule().getDate();
//            Date bookingTime = booking.getEmployeeSchedule().getStartTime();
//            Date currDate = dateErrorService.convertToDateType(dateAPI, "date");
//            Date currTime = dateErrorService.convertToDateType(timeAPI, "time");
//
//            // Check if date time is within 48 hours, otherwise cannot delete
//            // One day has 86,400,000 milliseconds
//            long dayToMilliSec = 86400000;
//            long dateDiff = (bookingDate.getTime() - currDate.getTime()) / dayToMilliSec;
//
//            if(dateDiff > 2) {
//                toBeDeleted = true;
//            } else if(dateDiff == 2) {
//                if(currTime.before(bookingTime)) {
//                    toBeDeleted = true;
//                } else {
//                    throw new ResponseException(HttpStatus.BAD_REQUEST, "Late request. Please contact business owner directly asap. Thank you");
//                }
//            } else {
//                throw new ResponseException(HttpStatus.BAD_REQUEST, "Late request. Please contact business owner directly asap. Thank you");
//            }
//        } else {
//            throw new ResponseException(HttpStatus.BAD_REQUEST, "Booking does not exist");
//        }
//
//        // Now its time to delete the booking and set the employee schedule availability to true
//        if(toBeDeleted) {
//            long employeeScheduleId = booking.getEmployeeSchedule().getId();
//            bookingRepository.deleteById(bookingId);
//            employeeScheduleRepository.updateEmployeeScheduleAfterBookingDeleted(employeeScheduleId);
//        }
//
//        return success;
//    }

//    public String testDateMinus(String bookingDateAPI, String bookingTimeAPI,
//                                String currDateAPI, String currTimeAPI) {
//        String statement = "";
//
//        Date bookingDate = dateErrorService.convertToDateType(bookingDateAPI, "date");
//        Date bookingTime = dateErrorService.convertToDateType(bookingTimeAPI, "time");
//        Date currDate = dateErrorService.convertToDateType(currDateAPI, "date");
//        Date currTime = dateErrorService.convertToDateType(currTimeAPI, "time");
//
//        // One day has 86,400,000 milliseconds
//        long dayToMilliSec = 86400000;
//        long dateDiff = (bookingDate.getTime() - currDate.getTime()) / dayToMilliSec;
//
//        if(dateDiff > 2) {
//            statement = "All good, can remove";
//        } else if(dateDiff == 2) {
//            if(currTime.before(bookingTime)) {
//                statement = "All good, can remove";
//            } else {
//                statement = "Too late, cannot remove";
//            }
//        } else {
//            statement = "Too late, cannot remove";
//        }
//
//        return statement + "\nDate: " + String.valueOf(dateDiff);
//    }
}
