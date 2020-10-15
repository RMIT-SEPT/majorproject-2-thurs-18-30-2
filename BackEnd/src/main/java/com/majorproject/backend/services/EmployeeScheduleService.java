package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.BService;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.repositories.EmployeeScheduleRepository;
import com.majorproject.backend.repositories.BServiceRepository;
import com.majorproject.backend.responseForms.EmpSchByEmpIdForm;
import com.majorproject.backend.responseForms.EmployeeByBServiceIdForm;
import com.majorproject.backend.responseForms.EmployeeScheduleAvailabilityForm;
import com.majorproject.backend.responseForms.EmployeeScheduleServicesAndDateForm;
import com.majorproject.backend.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class EmployeeScheduleService {
    @Autowired
    private EmployeeScheduleRepository employeeScheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BServiceRepository bServiceRepository;

    private IdErrorService idErrorService = new IdErrorService();
    private ListEmptyErrorService listEmptyErrorService = new ListEmptyErrorService();
    private ObjectEmptyErrorService objectEmptyErrorService = new ObjectEmptyErrorService();
    private DateErrorService dateErrorService = new DateErrorService();
    private DateNowUtil dateNowUtil = new DateNowUtil();

//    /**
//     * Creates the employee schedule
//     * @param request A map that contains the request details
//     * @return The employee schedule
//     */
//    public EmployeeSchedule saveEmployeeSchedule(Map<String, String> request) {
//        EmployeeSchedule employeeSchedule = new EmployeeSchedule();
//        EmployeeSchedule employeeScheduleNew = null;
//
//        try {
////            employeeSchedule.setDate(formatterDate.parse(request.get("date")));
////            employeeSchedule.setStartTime(formatterTime.parse(request.get("startTime")));
////            employeeSchedule.setEndTime(formatterTime.parse(request.get("endTime")));
//            employeeSchedule.setDate(dateErrorService.convertingDateType(request.get("date"), "date"));
//            employeeSchedule.setStartTime(dateErrorService.convertingDateType(request.get("startTime"), "startTime"));
//            employeeSchedule.setEndTime(dateErrorService.convertingDateType(request.get("endTime"), "endTime"));
//            employeeSchedule.setEmployee(employeeRepository.findByEmployeeId(Long.parseLong(request.get("employeeId"))));
//            employeeSchedule.setBService(bServiceRepository.getBServiceById(Long.parseLong(request.get("bServiceId"))));
//            employeeSchedule.setAvailability(true);
//        } catch(Exception e) {
//            throw new ResponseException(HttpStatus.BAD_REQUEST, "Scheduling error");
//        }
//
//        boolean foundDuplicates = duplicatedSchedule(employeeSchedule);
//        if(foundDuplicates) { // there is a duplicate
//            throw new ResponseException(HttpStatus.BAD_REQUEST, "Duplicated schedule");
//        } else { // No duplicate
//            employeeScheduleNew = employeeScheduleRepository.save(employeeSchedule);
//        }
//
//        return employeeScheduleNew;
//    }

    public EmployeeSchedule saveOrEditEmployeeSchedule(Map<String, String> request, String storingType, String scheduleIdAPI) {
        EmployeeSchedule employeeSchedule;
        String employeeIdString = request.get("employeeId");
        String bServiceIdString = request.get("bServiceId");
        String dateString = request.get("date");
        String startTimeString = request.get("startTime");
        String endTimeString = request.get("endTime");

//        boolean duplicated = checkForDuplicates(employeeIdString, bServiceIdString, dateString, startTimeString, endTimeString);
//        if(duplicated) {
//            throw new ResponseException(HttpStatus.BAD_REQUEST, "Duplicated schedule");
//        } else {
//            if(storingType.equals("save")) {
//                employeeSchedule = new EmployeeSchedule();
//
//                // Set availability
//                employeeSchedule.setAvailability(true); // set to true since its new schedule
//            } else { // if(storingType.equals("edit"))
//                Long scheduleId = idErrorService.idStringToLong(scheduleIdAPI);
//                employeeSchedule = employeeScheduleRepository.getEmployeeScheduleById(scheduleId);
//
//                // Set availability
//                boolean availability = Boolean.parseBoolean(request.get("availability"));
//                employeeSchedule.setAvailability(availability);
//            }
//
//            // Setting details
//            Long employeeId = idErrorService.idStringToLong(employeeIdString);
//            Employee employee = employeeRepository.findByEmployeeId(employeeId);
//            employeeSchedule.setEmployee(employee);
//
//            Long bServiceId = idErrorService.idStringToLong(bServiceIdString);
//            BService bService = bServiceRepository.getBServiceById(bServiceId);
//            employeeSchedule.setBService(bService);
//
//            Date date = dateErrorService.convertingDateType(request.get("date"), "date");
//            employeeSchedule.setDate(date);
//
//            Date startTime = dateErrorService.convertingDateType(request.get("startTime"), "time");
//            employeeSchedule.setStartTime(startTime);
//
//            Date endTime = dateErrorService.convertingDateType(request.get("endTime"), "time");
//            employeeSchedule.setEndTime(endTime);
//
//            employeeScheduleRepository.save(employeeSchedule);
//
//            return employeeSchedule;
//        }
        long duplicatedScheduleId = checkForDuplicates(employeeIdString, bServiceIdString, dateString, startTimeString, endTimeString);
        if(storingType.equals("save") && duplicatedScheduleId == -1) { // new employeeSchedule, no duplicates
            employeeSchedule = new EmployeeSchedule();

            // Set availability
            employeeSchedule.setAvailability(true); // set to true since its new schedule
        } else if(storingType.equals("edit")) {
            Long scheduleId = idErrorService.idStringToLong(scheduleIdAPI);

            /**
             * edit scheduleId = 3, details are all there
             *
             * if list id = 4, schedule duplicated
             * if list id = 3, correct schedule, edit it
             * if list id = -1, correct schedule, edit it
             */
            if(duplicatedScheduleId == scheduleId || duplicatedScheduleId == -1) { // no duplicates, editing the found one
                employeeSchedule = employeeScheduleRepository.getEmployeeScheduleById(scheduleId);

                // Set availability
                boolean availability = Boolean.parseBoolean(request.get("availability"));
                employeeSchedule.setAvailability(availability);
            } else { // duplicated, throw exception
                throw new ResponseException(HttpStatus.BAD_REQUEST, "Duplicated schedule");
            }
        } else { // duplicated, throw exception
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Duplicated schedule");
        }

        // Setting details
        Long employeeId = idErrorService.idStringToLong(employeeIdString);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        employeeSchedule.setEmployee(employee);

        Long bServiceId = idErrorService.idStringToLong(bServiceIdString);
        BService bService = bServiceRepository.getBServiceById(bServiceId);
        employeeSchedule.setBService(bService);

        Date date = dateErrorService.convertToDateType(dateString, "date");
        employeeSchedule.setDate(date);

        Date startTime = dateErrorService.convertToDateType(startTimeString, "time");
        employeeSchedule.setStartTime(startTime);

        Date endTime = dateErrorService.convertToDateType(endTimeString, "time");
        employeeSchedule.setEndTime(endTime);

        employeeScheduleRepository.save(employeeSchedule);

        return employeeSchedule;
    }

    // Repo used to find duplicates
//    public boolean checkForDuplicates(String employeeIdAPI, String bServiceIdAPI, String dateAPI, String startTimeAPI, String endTimeAPI) {
    public long checkForDuplicates(String employeeIdAPI, String bServiceIdAPI, String dateAPI, String startTimeAPI, String endTimeAPI) {
//        boolean found = false;
        EmployeeSchedule employeeSchedule;
        long duplicatedScheduleId = -1;

        long employeeId = idErrorService.idStringToLong(employeeIdAPI);
        long bServiceId = idErrorService.idStringToLong(bServiceIdAPI);
        Date date = dateErrorService.convertToDateType(dateAPI, "date");
        Date startTime = dateErrorService.convertToDateType(startTimeAPI, "time");
        Date endTime = dateErrorService.convertToDateType(endTimeAPI, "time");
//        List<EmployeeSchedule> employeeScheduleList = employeeScheduleRepository.getDuplicatedSchedules(employeeId, bServiceId, date, startTime, endTime);

        // Check if employee and bService exist
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        BService bService = bServiceRepository.getBServiceById(bServiceId);
        if(employee == null || bService == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Employee or BService error");
        }

        try {
            employeeSchedule = employeeScheduleRepository.getDuplicatedSchedules(employeeId, date, startTime, endTime);
        } catch(Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Schedule error");
        }

        if(employeeSchedule != null) {
            duplicatedScheduleId = employeeSchedule.getId();
        }

//        // if list is not empty, that means there is duplicates
//        if(!employeeScheduleList.isEmpty()) {
////            duplicatedScheduleId = employeeScheduleList.get(0).getId();
//            found = true;
//        }

//        return found;
        return duplicatedScheduleId;
    }

//    public EmployeeSchedule editSchedule(String scheduleIdAPI, Map<String, String> request) {
//        Long scheduleId = idErrorService.idStringToLong(scheduleIdAPI);
//        EmployeeSchedule employeeScheduleEdit = employeeScheduleRepository.getEmployeeScheduleById(scheduleId);
//
//        // Seting employee details
//        Long employeeId = idErrorService.idStringToLong(request.get("employeeId"));
//        Employee employee = employeeRepository.findByEmployeeId(employeeId);
//        employeeScheduleEdit.setEmployee(employee);
//
//        Long bServiceId = idErrorService.idStringToLong(request.get("bServiceId"));
//        BService bService = bServiceRepository.getBServiceById(bServiceId);
//        employeeScheduleEdit.setBService(bService);
//
//        Date date = dateErrorService.convertingDateType(request.get("date"), "date");
//        employeeScheduleEdit.setDate(date);
//
//        Date startTime = dateErrorService.convertingDateType(request.get("startTime"), "time");
//        employeeScheduleEdit.setStartTime(startTime);
//
//        Date endTime = dateErrorService.convertingDateType(request.get("endTime"), "time");
//        employeeScheduleEdit.setEndTime(endTime);
//
//        boolean availability = Boolean.parseBoolean(request.get("availability"));
//        employeeScheduleEdit.setAvailability(availability);
//
////        EmployeeSchedule emmployeeScheduleCurr = employeeScheduleRepository.getDuplicateSchedules(employeeId, bServiceId, date, startTime, endTime, availability);
////        if(emmployeeScheduleCurr == null) {
////            employeeScheduleEdit.setEmployee(employee);
////            employeeScheduleEdit.setBService(bService);
////            employeeScheduleEdit.setDate(date);
////            employeeScheduleEdit.setStartTime(startTime);
////            employeeScheduleEdit.setEndTime(endTime);
////            employeeScheduleEdit.setAvailability(availability);
////            employeeScheduleRepository.save(employeeScheduleEdit);
////        }
//
//        employeeScheduleRepository.save(employeeScheduleEdit);
//
//        return employeeScheduleEdit;
//    }

//    /**
//     * Find any duplicated employee schedule in the database
//     * If there are duplicates, return true
//     * If there are no duplicates, return false
//     * @param employeeSchedule The employee's schedule
//     * @return A boolean that determine if there is any duplicates
//     */
//    public boolean duplicatedSchedule(EmployeeSchedule employeeSchedule) {
//        boolean found = false;
//        List<EmployeeSchedule> employeeScheduleList = employeeScheduleRepository.getAllEmployeeSchedules();
//
//        // Checks employee, bService, date, startTime, endTime
//        for(int i = 0; i < employeeScheduleList.size() && !found; ++i) {
////            if(employeeSchedule.getEmployee().equals(employeeScheduleList.get(i).getEmployee()) &&
////                    employeeSchedule.getBService().equals(employeeScheduleList.get(i).getBService()) &&
////                    employeeSchedule.getDate().equals(employeeScheduleList.get(i).getDate()) &&
////                    employeeSchedule.getStartTime().equals(employeeScheduleList.get(i).getStartTime()) &&
////                    employeeSchedule.getEndTime().equals(employeeScheduleList.get(i).getEndTime())) {
////                found = true;
////            }
//            if(employeeSchedule.getEmployee().equals(employeeScheduleList.get(i).getEmployee()) &&
//            employeeSchedule.getDate().equals(employeeScheduleList.get(i).getDate())) {
//                Date scheduleStartTime = employeeSchedule.getStartTime();
//                Date scheduleEndTime = employeeSchedule.getEndTime();
//
//                Date listStartTime = employeeScheduleList.get(i).getStartTime();
//                Date listEndTime = employeeScheduleList.get(i).getEndTime();
//
//                if((scheduleStartTime.before(listStartTime) && scheduleEndTime.before(listStartTime)) ||
//                        (scheduleStartTime.after(listEndTime) && scheduleEndTime.after(listEndTime))) {
//
////                if((scheduleStartTime.after(listStartTime) && scheduleEndTime.before(listEndTime)) ||
////                        (scheduleStartTime.equals(listStartTime) && scheduleEndTime.equals(listEndTime)) ||
////                        (scheduleStartTime.after(listStartTime) && scheduleStartTime.before(listEndTime)) ||
////                        (scheduleEndTime.before(listEndTime) && scheduleEndTime.after(listStartTime))) {
//                    found = false;
//                } else {
//                    found = true;
//                }
//            }
//        }
//
//        return found;
//    }

    // Check for employee schedules, by date, start time and end time
//    public List<EmployeeScheduleAvailabilityForm> getSchedulesWithinTime(String dateAPI,
//                                                                         String startTimeAPI,
//                                                                         String endTimeAPI) {
    public ListWithTimeboundService getSchedulesWithinTime(String dateAPI,
                                                           String startTimeAPI,
                                                           String endTimeAPI) {
        List<EmployeeSchedule> employeeScheduleList;
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();

        Date date = dateErrorService.convertToDateType(dateAPI, "date");
        Date startTime = dateErrorService.convertToDateType(startTimeAPI, "time");
        Date endTime = dateErrorService.convertToDateType(endTimeAPI, "time");

        employeeScheduleList = employeeScheduleRepository.findSchedulesByDateAndTime(date, startTime, endTime);
        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee Schedule");

        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
        ListWithTimeboundService listWithTimeboundService = new ListWithTimeboundService(employeeScheduleAvailabilityList);
        return listWithTimeboundService;
//        return employeeScheduleAvailabilityList;
    }

    // Check for employee schedules, by employeeId
    public List<EmpSchByEmpIdForm> getSchedulesByEmployeeId(String employeeIdAPI, String repoCallType) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmpSchByEmpIdForm> customScheduleList = new ArrayList<EmpSchByEmpIdForm>();

        long employeeId = idErrorService.idStringToLong(employeeIdAPI);

        // Checking which repo call it would be
        if(repoCallType.equals("all")) {
            employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeId(employeeId);
        } else if(repoCallType.equals("byAvailable")) {
            Date currDate = dateNowUtil.getCurrentDate();
            Date currTime = dateNowUtil.getCurrentTime();
            employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeIdAvailability(employeeId, currDate, currTime);
        }

        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee Schedule");

        for(int i = 0; i < employeeScheduleList.size(); ++i) {
            customScheduleList.add(new EmpSchByEmpIdForm(employeeScheduleList.get(i)));
        }

        return customScheduleList;

//        return employeeScheduleAvailabilityList;
    }

    // Check for employee schedules, by employeeId and date
    public ListWithTimeboundService getSchedulesByEmployeeIdAndDate(String employeeIdAPI) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();

        long employeeId = idErrorService.idStringToLong(employeeIdAPI);
        Date date = dateNowUtil.getCurrentDate();
        Date week = dateNowUtil.getCurrentWeek();

        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeIdDate(employeeId, date, week);
        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee Schedule");

        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
        ListWithTimeboundService listWithTimeboundService = new ListWithTimeboundService(employeeScheduleAvailabilityList);
        return listWithTimeboundService;

//        return employeeScheduleAvailabilityList;
    }

//    // Check for employee schedules, by employeeId and date
//    public ListWithTimeboundService getSchedulesByEmployeeIdAndDate(String employeeIdAPI, String dateAPI, String weekAPI) {
//        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
//        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();
//
//        long employeeId = idErrorService.idStringToLong(employeeIdAPI);
//        Date date = dateErrorService.convertToDateType(dateAPI, "date");
//        Date week = dateErrorService.convertToDateType(weekAPI, "date");
//
//        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeIdDate(employeeId, date, week);
//        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee Schedule");
//
//        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
//        ListWithTimeboundService listWithTimeboundService = new ListWithTimeboundService(employeeScheduleAvailabilityList);
//        return listWithTimeboundService;
//
////        return employeeScheduleAvailabilityList;
//    }

    /**
     * Returns the employee schedule based on the schedule's Id
     * @param scheduleId The schedule's Id
     * @return The employee schedule
     */
    public EmployeeSchedule getEmployeeScheduleByScheduleId(Long scheduleId) {
        EmployeeSchedule employeeSchedule = employeeScheduleRepository.getEmployeeScheduleById(scheduleId);
        objectEmptyErrorService.checkIfNull(employeeSchedule, "Employee Schedule does not exist");

        return employeeSchedule;
    }

    public List<EmployeeByBServiceIdForm> getEmployeesByBServiceId(String bServiceIdAPI) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeByBServiceIdForm> employeeList = new ArrayList<EmployeeByBServiceIdForm>();

        long bServiceId = idErrorService.idStringToLong(bServiceIdAPI);

//        employeeScheduleList = employeeScheduleRepository.getEmployeeByBServiceId(bServiceId);
        employeeScheduleList = employeeScheduleRepository.getEmployeeByBServiceIdOnlyAvailable(bServiceId);
        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee");

        List<Long> storeEmployeeId = new ArrayList<Long>();
        for(int i = 0; i < employeeScheduleList.size(); ++i) {
            // Check for duplicated employeeId
            boolean duplicated = false;
            long listEmployeeId = employeeScheduleList.get(i).getEmployee().getId();

            for(int j = 0; j < storeEmployeeId.size() && !duplicated; ++j) {
                if(listEmployeeId == storeEmployeeId.get(j)) {
                    duplicated = true;
                }
            }

            if(!duplicated) {
                employeeList.add(new EmployeeByBServiceIdForm(employeeScheduleList.get(i)));
                storeEmployeeId.add(listEmployeeId);
            }
//            employeeList.add(new EmployeeByBServiceIdForm(employeeScheduleList.get(i)));
        }

        return employeeList;
    }

//    public ListWithTimeboundService getScheduleByBServiceId(String bServiceIdAPI) {
//        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
//        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();
//
//        long bServiceId = idErrorService.idStringToLong(bServiceIdAPI);
//
//        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByBServiceId(bServiceId);
//        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee Schedule");
//
//        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
//        ListWithTimeboundService listWithTimeboundService = new ListWithTimeboundService(employeeScheduleAvailabilityList);
//        return listWithTimeboundService;
//
////        return employeeScheduleAvailabilityList;
//    }

    public List<EmployeeScheduleAvailabilityForm> convertToAvailabilityList(List<EmployeeSchedule> listOld,
                                                                            List<EmployeeScheduleAvailabilityForm> listNew) {
        for(int i = 0; i < listOld.size(); ++i) {
            listNew.add(new EmployeeScheduleAvailabilityForm(listOld.get(i)));
        }

        return listNew;
    }

    public List<EmployeeByBServiceIdForm> getSchedulesByBServiceIdAndNow(String bServiceIdAPI) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeByBServiceIdForm> employeeList = new ArrayList<EmployeeByBServiceIdForm>();

        long bServiceId = idErrorService.idStringToLong(bServiceIdAPI);
        Date currDate = dateNowUtil.getCurrentDate();
        Date currTime = dateNowUtil.getCurrentTime();

//        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByBServiceIdAndNow(bServiceId, date, currTime);
        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByBServiceIdAndNowOnlyAvailable(bServiceId, currDate, currTime);
        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee");

        for(int i = 0; i < employeeScheduleList.size(); ++i) {
            employeeList.add(new EmployeeByBServiceIdForm(employeeScheduleList.get(i)));
        }

        return employeeList;
    }

    public ListWithTimeboundService getSchedulesByEmployeeAndBService(String employeeIdAPI, String bServiceIdAPI) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();

        long employeeId = idErrorService.idStringToLong(employeeIdAPI);
        long bServiceId = idErrorService.idStringToLong(bServiceIdAPI);
        Date currDate = dateNowUtil.getCurrentDate();
        Date currTime = dateNowUtil.getCurrentTime();

        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeIdAndBServiceIdAndNow(employeeId, bServiceId,
                                                                                                        currDate, currTime);
        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee Schedule");

        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
        ListWithTimeboundService listWithTimeboundService = new ListWithTimeboundService(employeeScheduleAvailabilityList);
        return listWithTimeboundService;
    }

//    /**
//     * Returns a custom list(see form class) that contains the employee's availability based on the employee's id
//     * and week if byWeek = true
//     * @param employeeId The employee's Id
//     * @param byWeek A boolean that checks if the list should only contain dates from today till a week
//     * @return A custom list that contains the employee's availability
//     */
//    public List<EmployeeScheduleAvailabilityForm> getEmployeeAvailability(Long employeeId, boolean byWeek) {
//        List<EmployeeSchedule> employeeScheduleList;
//        if(byWeek) { // Get only today till next week
//            try {
//                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                LocalDateTime currentDate = LocalDateTime.now();
//                Date now = formatterDate.parse(currentDate.format(dtf));
//                LocalDateTime currentWeekFromDate = currentDate.plusWeeks(1);
//                Date nextWeekDate = formatterDate.parse(currentWeekFromDate.format(dtf));
//                employeeScheduleList = employeeScheduleRepository.getEmployeeAvailabilityByIdInWeek(employeeId, now, nextWeekDate);
//            } catch(Exception e) {
//                throw new ResponseException(HttpStatus.BAD_REQUEST, "Date Error");
//            }
//
//        } else { // Get all employee availability
//            employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeId(employeeId);
//        }
//
//        if(employeeScheduleList.isEmpty()) {
//            throw new ResponseException(HttpStatus.BAD_REQUEST, "No time available");
//        }
//
//        List<EmployeeScheduleAvailabilityForm> employeeAvailability = new ArrayList<EmployeeScheduleAvailabilityForm>();
//
//        for(int i = 0; i < employeeScheduleList.size(); ++i) {
//            employeeAvailability.add(new EmployeeScheduleAvailabilityForm(employeeScheduleList.get(i)));
//        }
//
//        return employeeAvailability;
//    }

    /*** Future code ***/

////    public EmployeeSchedule editEmployeeSchedule(EmployeeSchedule employeeSchedule) {
//    public EmployeeSchedule editEmployeeSchedule(String id, Map <String, String> request) {
//        Long scheduleId = Long.parseLong(id);
//        EmployeeSchedule employeeScheduleEdit = getEmployeeScheduleById(scheduleId);
//
//        try {
//            employeeScheduleEdit.setEmployee(employeeRepository.findByEmployeeId(Long.parseLong(request.get("employeeId"))));
//            employeeScheduleEdit.setBService(bServiceRepository.findByBServiceId(Long.parseLong(request.get("bServiceId"))));
//            employeeScheduleEdit.setDate(formatterDate.parse(request.get("date")));
//            employeeScheduleEdit.setStartTime(formatterTime.parse(request.get("startTime")));
//            employeeScheduleEdit.setEndTime(formatterTime.parse(request.get("endTime")));
//        } catch(Exception e) {
//            throw new ResponseException(HttpStatus.BAD_REQUEST, "Scheduling error");
//        }
//
//        boolean foundDuplicates = findAnyDuplicates(employeeScheduleEdit);
//        if(foundDuplicates) { // there is a duplicate
//            throw new ResponseException(HttpStatus.BAD_REQUEST, "Duplicated schedule");
//        } else { // No duplicate
//            employeeScheduleEdit = employeeScheduleRepository.save(employeeScheduleEdit);
//        }
//
//        return employeeScheduleEdit;
//    }
}
