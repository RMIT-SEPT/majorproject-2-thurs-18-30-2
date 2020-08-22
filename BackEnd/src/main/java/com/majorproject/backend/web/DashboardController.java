package com.majorproject.backend.web;

import com.majorproject.backend.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/value = {'customer', 'employee'}/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @PostMapping("")
    public ResponseEntity<?> viewDashboard() {
//        return new ResponseEntity<Dashboard>(???, HttpStatus.OK);
    }
}
