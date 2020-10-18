package com.majorproject.backend;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.exceptions.RestExceptionHandler;
import com.majorproject.backend.models.BService;
import com.majorproject.backend.security.JwtAuthenticationEntryPoint;
import com.majorproject.backend.security.JwtTokenProvider;
import com.majorproject.backend.services.CustomUserDetailsService;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.services.BServiceService;
import com.majorproject.backend.util.Util;
import com.majorproject.backend.web.BServiceController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BServiceController.class)
public class BServiceControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BServiceService bServiceService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private JwtTokenProvider tokenProvider;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private BindingResult result;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    private BService bService;
    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    @Before
    public void serviceObject() {

    }

    @Test
    public void serviceCreate_Pass() throws Exception {
        bService = new BService("name", "description");

        mvc.perform(post("/api/bService/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(bService))).andExpect(status().isOk());
    }
}