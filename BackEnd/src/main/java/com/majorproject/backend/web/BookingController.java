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

        return  response;
    }

//    @GetMapping("/getBookings")
//    public ResponseEntity<?> getBookings(@RequestParam Map<String,String> requestParams) {
//        List<Booking> bookingList = bookingService.displayDashboard(requestParams.get("id"), requestParams.get(""));
//        return new ResponseEntity<List<Booking>>(bookingList, HttpStatus.OK);
//    }

    @GetMapping("getBookings/{username}")
    public ResponseEntity<?> getUserBookings(@Valid @PathVariable String username) {
        List<Booking> bookingList = bookingService.getBookingsByUser(username);
        ResponseEntity<?> response = new ResponseEntity<List<Booking>>(bookingList, HttpStatus.OK);

        return response;
    }
}
