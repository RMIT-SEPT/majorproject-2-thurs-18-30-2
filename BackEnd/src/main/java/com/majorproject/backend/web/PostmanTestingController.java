package com.majorproject.backend.web;

import com.majorproject.backend.services.DateErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/postmanTesting")
@CrossOrigin
public class PostmanTestingController {

    private DateErrorService dateErrorService = new DateErrorService();

    @GetMapping("/test/default/getDate")
    public ResponseEntity<?> getDefaultDate() {
        String dateAPI = "2020-09-10";
        Date date = dateErrorService.convertToDateType(dateAPI, "date");

        String getDate = date.toString();
        return new ResponseEntity<String>(getDate, HttpStatus.OK);
    }

    @GetMapping("/test/getDate")
    public ResponseEntity<?> getDate() {
        String dateAPI = "2020-09-10";
        Date date = dateErrorService.convertToDateType(dateAPI, "date");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = dateFormat.format(date);
        List<String> list = Arrays.asList(dateInString.split("-"));

        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

    @GetMapping("/test/getTime")
    public ResponseEntity<?> getTime() {
        String timeAPI = "08:00";
        Date time = dateErrorService.convertToDateType(timeAPI, "time");

        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String timeInString = timeFormat.format(time);
        List<String> list = Arrays.asList(timeInString.split(":"));

        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

    @GetMapping("/test/getDateTime")
    public ResponseEntity<?> getDateTime() {
        String dateAPI = "2020-10-10";
        String startTimeAPI = "08:00";
        String endTimeAPI = "10:00";

        // Reading from FrontEnd
        Date date = dateErrorService.convertToDateType(dateAPI, "date");
        Date startTime = dateErrorService.convertToDateType(startTimeAPI, "time");
        Date endTime = dateErrorService.convertToDateType(endTimeAPI, "time");

        // Reading from BackEnd
        String dateInString = dateErrorService.convertToStringType(date, "date");
        String startTimeInString = dateErrorService.convertToStringType(startTime, "time");
        String endTimeInString = dateErrorService.convertToStringType(endTime, "time");

        List<String> dateList = Arrays.asList(dateInString.split("-"));

        // Remove 0 from Month
        if(dateList.get(1).substring(0, 1).equals("0")) {
            dateList.set(1, dateList.get(1).substring(1, 2));
        }

        List<String> startTimeList = Arrays.asList(startTimeInString.split(":"));
        List<String> endTimeList = Arrays.asList(endTimeInString.split(":"));

        List<String> startDateList = new ArrayList<String>();
        startDateList.addAll(dateList);
        startDateList.addAll(startTimeList);

        List<String> endDateList = new ArrayList<String>();
        endDateList.addAll(dateList);
        endDateList.addAll(endTimeList);

        List<List<String>> listString = new ArrayList<List<String>>();
        listString.addAll(Collections.singleton(startDateList));
        listString.addAll(Collections.singleton(endDateList));

        return new ResponseEntity<List<List<String>>>(listString, HttpStatus.OK);
    }
}
