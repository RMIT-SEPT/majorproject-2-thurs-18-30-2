package com.majorproject.backend;

import com.majorproject.backend.models.BService;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.services.BServiceService;
import com.majorproject.backend.util.Util;
import com.majorproject.backend.web.BServiceController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BServiceController.class)
public class BServiceControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BServiceService BServiceService;
    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    private BService BService;

    @Before
    public void serviceObject() {
        BService = new BService();
    }

    @Test
    public void serviceCreate_Pass() throws Exception {
        mvc.perform(post("/api/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(BService))).andExpect(status().isCreated());
    }

    @Test
    public void serviceCreate_Fail() throws Exception {
        BService = null;

        mvc.perform(post("/api/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(BService)))
                .andExpect(status().is4xxClientError());
    }
}