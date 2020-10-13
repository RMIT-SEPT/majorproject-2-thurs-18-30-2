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
import javax.xml.ws.Response;
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
        List<BookingMainForm> bookingMainFormList = bookingService.getAllBookings();
        ResponseEntity<?> response = new ResponseEntity<List<BookingMainForm>>(bookingMainFormList, HttpStatus.OK);

        return response;
    }

    @GetMapping("getBookings/{userTypeAPI}/{idAPI}")
    public ResponseEntity<?> getUserBookings(@Valid @PathVariable String userTypeAPI, @PathVariable String idAPI) {
        List<BookingMainForm> bookingMainFormList = bookingService.getBookingsForUserById(userTypeAPI, idAPI);
        ResponseEntity<?> response = new ResponseEntity<List<BookingMainForm>>(bookingMainFormList, HttpStatus.OK);

        return response;
    }

    @GetMapping("/deleteBooking/{bookingIdAPI}")
    public ResponseEntity<?> deleteBooking(@Valid @PathVariable String bookingIdAPI) {
        String success = bookingService.deleteBooking(bookingIdAPI);
        return new ResponseEntity<String>(success, HttpStatus.OK);
    }

//    @GetMapping("/deleteBooking/{bookingIdAPI}/{currDateAPI}/{currTimeAPI}")
//    public ResponseEntity<?> deleteBooking(@Valid @PathVariable String bookingIdAPI, @PathVariable String currDateAPI,
//                                           @PathVariable String currTimeAPI) {
//        String success = bookingService.deleteBooking(bookingIdAPI, currDateAPI, currTimeAPI);
//        return new ResponseEntity<String>(success, HttpStatus.OK);
//    }

//    @GetMapping("/test/{bookingDateAPI}/{bookingTimeAPI}/{currDateAPI}/{currTimeAPI}")
//    public ResponseEntity<?> testDateMinus(@Valid @PathVariable String bookingDateAPI, @PathVariable String bookingTimeAPI,
//                                           @PathVariable String currDateAPI, @PathVariable String currTimeAPI) {
//        String result = bookingService.testDateMinus(bookingDateAPI, bookingTimeAPI, currDateAPI, currTimeAPI);
//        return new ResponseEntity<String>(result, HttpStatus.OK);
//    }

//    /**
//     * Gets the bookings based on the user's username
//     * @param usernameAPI The user's username
//     * @return A response entity of the list of bookings based on the user's username
//     */
//    @GetMapping("getBookings/{usernameAPI}")
//    public ResponseEntity<?> getUserBookings(@Valid @PathVariable String usernameAPI) {
//        List<Booking> bookingList = bookingService.getBookingsByUser(usernameAPI);
//        ResponseEntity<?> response = new ResponseEntity<List<Booking>>(bookingList, HttpStatus.OK);
//
//        return response;
//    }
}
