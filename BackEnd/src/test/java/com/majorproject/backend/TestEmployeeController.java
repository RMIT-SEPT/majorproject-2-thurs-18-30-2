//package com.majorproject.backend;
//
//import com.majorproject.backend.models.Employee;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//public class TestEmployeeController {
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    public void createEmployeeAPI() throws Exception {
//        mvc.perform(MockMvcRequestBuilders
//                .post("api/employee")
//                .content(asJsonString(new Employee("first", "last", "test@gmail.com", "pass", "Test Address", "1234567890", "Admin")))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .adExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
//    }
//
//    public static String asJsonString(final Object obj) throws Exception {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
