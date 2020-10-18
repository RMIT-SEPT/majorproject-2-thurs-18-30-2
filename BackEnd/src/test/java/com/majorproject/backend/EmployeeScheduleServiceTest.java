package com.majorproject.backend;

import com.majorproject.backend.models.*;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.repositories.EmployeeScheduleRepository;
import com.majorproject.backend.security.JwtAuthenticationEntryPoint;
import com.majorproject.backend.security.JwtTokenProvider;
import com.majorproject.backend.services.BookingService;
import com.majorproject.backend.services.CustomUserDetailsService;
import com.majorproject.backend.services.EmployeeScheduleService;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.util.DateErrorUtil;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeScheduleService.class)
public class EmployeeScheduleServiceTest {
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
    private EmployeeScheduleRepository employeeScheduleRepository;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeScheduleService employeeScheduleService;

    private EmployeeSchedule employeeSchedule;
    private DateErrorUtil dateErrorUtil = new DateErrorUtil();

    @Before
    public void run() {
        employeeSchedule = new EmployeeSchedule();

        Employee employee = new Employee();
        long employeeId = 1;
        employee.setId(employeeId);

        employeeSchedule.setEmployee(employee);

        BService bService = new BService();
        long bServiceId = 1;
        bService.setId(bServiceId);

        employeeSchedule.setBService(bService);

        Date date = dateErrorUtil.convertToDateType("2020-11-18", "date");
        employeeSchedule.setDate(date);

        Date startTime = dateErrorUtil.convertToDateType("14:00", "time");
        employeeSchedule.setStartTime(startTime);

        Date endTime = dateErrorUtil.convertToDateType("16:00", "time");
        employeeSchedule.setEndTime(endTime);
    }

    @Test
    public void employeeScheduleCreate_Success() throws Exception {
        Map<String, String> request = new HashMap<String, String>();
        request.put("employeeId", "1");
        request.put("bServiceId", "1");
        request.put("date", "2020-11-18");
        request.put("startTime", "14:00");
        request.put("endTime", "16:00");

        given(employeeScheduleService.saveOrEditEmployeeSchedule(request, "save", null)).willReturn(employeeSchedule);
        EmployeeSchedule actualEmployeeSchedule = employeeScheduleService.saveOrEditEmployeeSchedule(request, "save", null);

        Assert.assertEquals(actualEmployeeSchedule.getDate(), employeeSchedule.getDate());
    }
}
