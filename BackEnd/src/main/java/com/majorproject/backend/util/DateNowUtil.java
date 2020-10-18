package com.majorproject.backend.util;

import com.majorproject.backend.exceptions.ResponseException;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * This class takes in the current date and splits it into relevant dates for backend date processing
 */
public class DateNowUtil {
    public Date getCurrentDate() {
        Date currDate = null;
        String datePattern = "yyyy-MM-dd";

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern);
        SimpleDateFormat formatterDate = new SimpleDateFormat(datePattern);

        try {
            currDate = formatterDate.parse(now.format(dtf));
        } catch(ParseException e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Date Error");
        }


        return currDate;
    }

    public Date getCurrentWeek() {
        Date nextWeekDate = null;
        String datePattern = "yyyy-MM-dd";

        LocalDateTime currentWeekFromDate = LocalDateTime.now().plusWeeks(1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern);
        SimpleDateFormat formatterDate = new SimpleDateFormat(datePattern);

        try {
            nextWeekDate = formatterDate.parse(currentWeekFromDate.format(dtf));
        } catch(ParseException e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Date Error");
        }

        return nextWeekDate;
    }

    public Date getCurrentTime() {
        Date currTime = null;
        String timePattern = "HH:mm";

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(timePattern);
        SimpleDateFormat formatterTime = new SimpleDateFormat(timePattern);

        try {
            currTime = formatterTime.parse(now.format(dtf));
        } catch(ParseException e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Date Error");
        }

        return currTime;
    }
}
