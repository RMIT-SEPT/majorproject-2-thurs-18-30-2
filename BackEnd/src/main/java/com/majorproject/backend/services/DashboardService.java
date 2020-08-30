package com.majorproject.backend.services;

import com.majorproject.backend.dashboard.Dashboard;
import com.majorproject.backend.models.Booking;
import com.majorproject.backend.models.User;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.DashboardRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired
    private DashboardRepository dashboardRepository;
    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;

    public Dashboard displayDashboard(String username) {
        Dashboard dashboard = null;
        User user = customerRepository.findByUsername(username);
        if(user != null) {
            dashboard = dashboardRepository.refreshCustomerDashboard(username);
        } else {
            user = employeeRepository.findByUsername(username);
            dashboard = dashboardRepository.refreshEmployeeDashboard(username);
        }

        return dashboard;
    }
}
