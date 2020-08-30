package com.majorproject.backend.web;

import com.majorproject.backend.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/value = {'customer', 'employee'}/{username}/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @PostMapping("/view")
    public ResponseEntity<?> viewDashboard(@PathVariable String username) {
        ResponseEntity<?> responseEntity = null;
        dashboardService.displayDashboard(username);
//        return new ResponseEntity<Dashboard>(???, HttpStatus.OK);
        return responseEntity;
    }
}
