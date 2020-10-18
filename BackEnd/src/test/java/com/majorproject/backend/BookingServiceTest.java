package com.majorproject.backend;

import com.majorproject.backend.models.Booking;
import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.repositories.*;
import com.majorproject.backend.security.JwtAuthenticationEntryPoint;
import com.majorproject.backend.security.JwtTokenProvider;
import com.majorproject.backend.services.BServiceService;
import com.majorproject.backend.services.BookingService;
import com.majorproject.backend.services.CustomUserDetailsService;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.util.DateErrorUtil;
import com.majorproject.backend.util.DateNowUtil;
import com.majorproject.backend.util.IdErrorUtil;
import com.majorproject.backend.util.ListEmptyErrorUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingService.class)
public class BookingServiceTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private MapValidationErrorService mapValidationErrorService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private JwtTokenProvider tokenProvider;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @MockBean
    private BookingService bookingService;
    @MockBean
    private BookingRepository bookingRepository;

    private Booking booking;

    @Before
    public void runBefore() {
        EmployeeSchedule employeeSchedule = new EmployeeSchedule();
        Customer customerJohn =  new Customer("John", "Apple", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678");
        booking = new Booking(employeeSchedule, customerJohn);
    }

    @Test
    public void bookingCreate_Success() throws Exception {
        given(bookingService.saveOrUpdateBooking(booking)).willReturn(booking);
        Booking actualBooking = bookingService.saveOrUpdateBooking(booking);

        Assert.assertEquals(actualBooking, booking);
    }

    @Test
    public void deleteBookings() throws Exception {
        List<Booking> bookingList = new ArrayList<Booking>();
        bookingList.add(booking);
        Assert.assertEquals(bookingList.size(), 1);

        String success = "Booking is successfully deleted";
        String bookingIdString = "1";
        long bookingId = 1;

        given(bookingService.deleteBooking(bookingIdString)).willReturn(success);

        String result = bookingService.deleteBooking("1");
        Assert.assertEquals(success, result);

        if(result.equals(success)) {
            bookingList.remove(0);
        }

        Assert.assertEquals(bookingList.size(), 0);
    }
}
