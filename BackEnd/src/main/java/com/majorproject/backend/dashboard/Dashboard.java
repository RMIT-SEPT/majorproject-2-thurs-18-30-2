package com.majorproject.backend.dashboard;

import com.majorproject.backend.models.Booking;
import com.majorproject.backend.models.User;

import java.util.ArrayList;

public class Dashboard {
    private String username;
    private ArrayList<Booking> dashboard = new ArrayList<Booking>();

    public Dashboard(String username) {
        this.username = username;
    }
}
