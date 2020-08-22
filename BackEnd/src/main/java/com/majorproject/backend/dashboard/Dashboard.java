package com.majorproject.backend.dashboard;

import com.majorproject.backend.models.Booking;

import java.util.ArrayList;

public class Dashboard {
    private ArrayList<Booking> bookings = new ArrayList<Booking>();

    public ArrayList<Booking> displayBookings() {
        return bookings;
    }
}
