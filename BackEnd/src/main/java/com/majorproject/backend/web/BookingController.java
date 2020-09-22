package com.majorproject.backend.web;

import com.majorproject.backend.models.Booking;
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

    @GetMapping("getAllBookings")
    public ResponseEntity<?> getAllBookings() {
        List<Booking> bookingList = bookingService.getAllBookings();
        ResponseEntity<?> response = new ResponseEntity<List<Booking>>(bookingList, HttpStatus.OK);

        return response;
    }

    /**
     * Gets the bookings based on the user's username
     * @param username The user's username
     * @return A response entity of the list of bookings based on the user's username
     */
    @GetMapping("getBookings/{username}")
    public ResponseEntity<?> getUserBookings(@Valid @PathVariable String username) {
        List<Booking> bookingList = bookingService.getBookingsByUser(username);
        ResponseEntity<?> response = new ResponseEntity<List<Booking>>(bookingList, HttpStatus.OK);

        return response;
    }
}
