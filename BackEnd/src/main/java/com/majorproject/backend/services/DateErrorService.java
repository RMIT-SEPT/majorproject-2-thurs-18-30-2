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

    /*** Convert from String to Date ***/

    public Date convertToDateType(String variableAPI, String dateType) {
        Date variable;
        try {
             if(dateType.equals("date")) {
                 variable = dateStringToDate(variableAPI);
             } else { // if(dateType.equals("time"))
                 variable = timeStringToTime(variableAPI);
             }
        } catch (Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Date Error");
        }

        return variable;
    }

    public Date dateStringToDate(String dateAPI) throws ParseException {
        Date date = formatterDate.parse(dateAPI);
        return date;
    }

    public Date timeStringToTime(String timeAPI) throws ParseException {
        Date time = formatterTime.parse(timeAPI);
        return time;
    }

    /*** Convert from Date to String ***/

    public String convertToStringType(Date variable, String dateType) {
        String variableInString;
        try {
            if(dateType.equals("date")) {
                variableInString = dateToDateString(variable);
            } else { // if(dateType.equals("time"))
                variableInString = timeToTimeString(variable);
            }
        } catch(Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Date Error");
        }

        return variableInString;
    }

    public String dateToDateString(Date date) throws ParseException {
        String dateInString = formatterDate.format(date);
        return dateInString;
    }

    public String timeToTimeString(Date time) throws ParseException {
        String timeInString = formatterTime.format(time);
        return timeInString;
    }
}
