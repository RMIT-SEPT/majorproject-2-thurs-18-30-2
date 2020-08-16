package com.majorproject.backend.web;

import com.majorproject.backend.jsonconv.BookingForm;
import com.majorproject.backend.models.Booking;
import com.majorproject.backend.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("")
    public ResponseEntity<?> createBooking(@RequestBody BookingForm bookingForm) {
        Booking booking1 = bookingService.saveOrUpdateBooking(bookingForm);
        return new ResponseEntity<Booking>(booking1, HttpStatus.CREATED);
    }
}
