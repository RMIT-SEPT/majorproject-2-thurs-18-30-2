package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateErrorService {
    // String to Date format
    private SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");

    // String to Time format
    private SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");

    // To not have duplicated try-catch and exception code
    public Date convertingDateType(String variableAPI, String dateType) {
        Date date;
        try {
             if(dateType.equals("date")) {
                 date = dateStringToDate(variableAPI);
             } else if(dateType.equals("startTime")) {
                 date = startTimeStringToDate(variableAPI);
             } else { // if(dateType.equals("endTime"))
                 date = endTimeStringToDate(variableAPI);
             }
        } catch (Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Date Error");
        }

        return date;
    }

    public Date dateStringToDate(String dateAPI) throws ParseException {
        Date date = formatterDate.parse(dateAPI);
        return date;
    }

    public Date startTimeStringToDate(String startTimeAPI) throws ParseException {
        Date startTime = formatterTime.parse(startTimeAPI);
        return startTime;
    }

    public Date endTimeStringToDate(String endTimeAPI) throws ParseException {
        Date endTime = formatterTime.parse(endTimeAPI);
        return endTime;
    }
}
