package com.majorproject.backend.services;

import com.majorproject.backend.dashboard.Dashboard;
import com.majorproject.backend.models.Booking;
import com.majorproject.backend.repositories.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired
    private DashboardRepository dashboardRepository;

    public Dashboard displayDashboard(String userType) {
        // Read dashboard
//        return dashboardRepository.findAll();
    }
}
