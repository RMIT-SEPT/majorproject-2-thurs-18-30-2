package com.majorproject.backend;

import com.majorproject.backend.models.BService;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.services.BServiceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(BServiceService.class)
public class ServiceBServiceTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BServiceService bServiceService;
    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    private BService bService;

    @Before
    public void serviceObject() {
        bService = new BService();
    }

    @Test
    public void serviceCreate_Pass() throws Exception {
        given(bServiceService.saveOrUpdateBService(bService)).willReturn(bService);

        BService actualBService = bServiceService.saveOrUpdateBService(bService);
        Assert.assertEquals(actualBService, bService);
    }

    @Test
    public void serviceCreate_Fail() throws Exception {
        BService bService2 = new BService();

        given(bServiceService.saveOrUpdateBService(bService)).willReturn(bService);
        given(bServiceService.saveOrUpdateBService(bService)).willReturn(bService);

        BService actualBService = bServiceService.saveOrUpdateBService(bService2);
        Assert.assertNotEquals(actualBService, bService);
    }
}