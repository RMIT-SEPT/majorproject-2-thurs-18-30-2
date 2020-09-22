package com.majorproject.backend;

import com.majorproject.backend.repositories.*;
import com.majorproject.backend.services.MapValidationErrorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
class BackendApplicationTests {
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeScheduleRepository employeeScheduleRepository;
    @MockBean
    private BServiceRepository bServiceRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private BookingRepository bookingRepository;
    @MockBean
    private MapValidationErrorService mapValidationErrorService;
    @Test
    void contextLoads() {
    }
}
