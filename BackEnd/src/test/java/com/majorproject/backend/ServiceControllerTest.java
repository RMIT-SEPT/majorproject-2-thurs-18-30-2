package com.majorproject.backend;

import com.majorproject.backend.models.Services;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.services.CustomerService;
import com.majorproject.backend.services.EmployeeService;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.services.ServiceService;
import com.majorproject.backend.util.Util;
import com.majorproject.backend.web.EmployeeController;
import com.majorproject.backend.web.ServiceController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.validation.BindingResult;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceController.class)
public class ServiceControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ServiceService serviceService;
    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    private Services service;

    @Before
    public void serviceObject() {
        service = new Services();
    }

    @Test
    public void serviceCreate_Pass() throws Exception {
        mvc.perform(post("/api/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(service))).andExpect(status().isCreated());
    }

    @Test
    public void serviceCreate_Fail() throws Exception {
        service = null;

        mvc.perform(post("/api/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(service)))
                .andExpect(status().is4xxClientError());
    }
}