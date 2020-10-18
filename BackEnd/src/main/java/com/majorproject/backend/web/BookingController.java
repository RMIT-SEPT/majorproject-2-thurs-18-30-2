package com.majorproject.backend.web;

import com.majorproject.backend.models.Booking;
import com.majorproject.backend.responseForms.BookingMainForm;
import com.majorproject.backend.services.BookingService;
import com.majorproject.backend.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/booking")
@CrossOrigin
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     * Creates the booking
     * @param booking The booking
     * @param result BindingResult
     * @return A response entity of the registered booking
     */
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking, BindingResult result) {
        ResponseEntity<?> response;

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) {
            response = errorMap;
        } else {
            Booking booking1 = bookingService.saveOrUpdateBooking(booking);
            response = new ResponseEntity<Booking>(booking1, HttpStatus.CREATED);
        }

        return response;
    }

    /**
     * Gets all bookings, based on if past or current
     * @param state The state of booking (past or current)
     * @return A custom list of bookings based on above
     */
    @GetMapping("getAllBookings/{state}")
    public ResponseEntity<?> getAllBookings(@Valid @PathVariable String state) {
        List<BookingMainForm> bookingMainFormList = bookingService.getAllBookings(state);
        ResponseEntity<?> response = new ResponseEntity<List<BookingMainForm>>(bookingMainFormList, HttpStatus.OK);

        return response;
    }

    /**
     * Gets a list of bookings for a user, but only past or current based on user's choice
     * @param userTypeAPI The userType (employee or customer)
     * @param idAPI The user id
     * @param state The state of booking (past or current)
     * @return A custom list of bookings based on above
     */
    @GetMapping("getBookings/{userTypeAPI}/{idAPI}/{state}")
    public ResponseEntity<?> getUserBookings(@Valid @PathVariable String userTypeAPI, @PathVariable String idAPI, @PathVariable String state) {
        List<BookingMainForm> bookingMainFormList = bookingService.getBookingsForUserById(userTypeAPI, idAPI, state);
        ResponseEntity<?> response = new ResponseEntity<List<BookingMainForm>>(bookingMainFormList, HttpStatus.OK);

        return response;
    }

    /**
     * Removes a booking based on the bookingId
     * @param bookingIdAPI The bookingId
     * @return A String that says the booking is successfully deleted, if successful
     */
    @DeleteMapping("/deleteBooking/{bookingIdAPI}")
    public ResponseEntity<?> deleteBooking(@Valid @PathVariable String bookingIdAPI) {
        String success = bookingService.deleteBooking(bookingIdAPI);
        return new ResponseEntity<String>(success, HttpStatus.OK);
    }
}
