package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeScheduleRepository extends CrudRepository<EmployeeSchedule, Long> {
    @Override
    Iterable<EmployeeSchedule> findAllById(Iterable<Long> iterable);

    /**
     * This query returns a list that contains all rows in Employee Schedule database
     * @return A list based on ^
     */
    @Query(value = "SELECT es.* FROM Employee_Schedule es", nativeQuery = true)
    List<EmployeeSchedule> getAllEmployeeSchedules();

    /**
     * This query returns an employee schedule based on the schedule's id
     * @param scheduleId The schedule's id
     * @return An employee schedule
     */
    @Query(value = "SELECT es.* FROM Employee_Schedule es WHERE es.employee_schedule_id = ?1", nativeQuery = true)
    EmployeeSchedule getEmployeeScheduleById(long scheduleId);

    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, Employee e " +
            "WHERE es.employee_id = e.employee_id AND " +
            "e.employee_id = ?1 " +
            "ORDER BY es.date", nativeQuery = true)
    List<EmployeeSchedule> getEmployeeScheduleByEmployeeId(long employeeId);

    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, Employee e " +
            "WHERE es.employee_id = e.employee_id AND " +
            "es.availability = true AND " +
            "e.employee_id = ?1 " +
            "ORDER BY es.date", nativeQuery = true)
    List<EmployeeSchedule> getEmployeeScheduleByEmployeeIdAvailability(long employeeId);

    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, Employee e " +
            "WHERE es.employee_id = e.employee_id AND " +
            "e.employee_id = ?1 AND " +
            "es.date = ?2 " +
            "ORDER BY es.date", nativeQuery = true)
    List<EmployeeSchedule> getEmployeeScheduleByEmployeeIdDate(long employeeId, Date date);

    /**
     * This query returns a list of employee schedules that are based on the employee's id,
     * today's date and next week's date
     * @param employeeId The employee's id
     * @param now Today's date
     * @param nextWeekDate Next week's date
     * @return A list based on ^
     */
    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, Employee e " +
            "WHERE es.employee_id = e.employee_id AND " +
            "es.availability = true AND " +
            "e.employee_id = ?1 AND " +
            "es.date >= ?2 AND " +
            "es.date <= ?3 " +
            "ORDER BY es.date", nativeQuery = true)
    List<EmployeeSchedule> getEmployeeAvailabilityByIdInWeek(long employeeId, Date now, Date nextWeekDate);

    /**
     * This query returns a list of employee schedules that are based on the bService's id,
     * and the specified date, start time and end time
     * @param bServiceId The bService's id
     * @param date The specified date
     * @param startTime The specified start time
     * @param endTime The specified end time
     * @return A list based on ^
     */
    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, Employee e, BService bs " +
            "WHERE es.employee_id = e.employee_id AND " +
            "es.service_id = bs.bservice_id AND " +
            "es.availability = true AND " +
            "bs.bservice_id = ?1 AND " +
            "es.date = ?2 AND " +
            "es.start_time >= ?3 AND " +
            "es.end_time <= ?4", nativeQuery = true)
    List<EmployeeSchedule> findSchedulesWithinParameters(long bServiceId, Date date, Date startTime, Date endTime);

    /**
     * This query returns a list of employee schedules that are based on the bService's id,
     * the employee's id and the specified date, start time and end time
     * @param bServiceId The bService's id
     * @param employeeId The employee's id
     * @param date The specified date
     * @param startTime The specified start time
     * @param endTime The specified end time
     * @return A list based on ^
     */
    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, Employee e, BService bs " +
            "WHERE es.employee_id = e.employee_id AND " +
            "es.service_id = bs.bservice_id AND " +
            "es.availability = true AND " +
            "bs.bservice_id = ?1 AND " +
            "e.employee_id = ?2 AND " +
            "es.date = ?3 AND " +
            "es.start_time >= ?4 AND " +
            "es.end_time <= ?5", nativeQuery = true)
    List<EmployeeSchedule> findSchedulesWithinParameters(long bServiceId, long employeeId, Date date, Date startTime, Date endTime);

    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, Employee e, BService bs " +
            "WHERE es.employee_id = e.employee_id AND " +
            "es.service_id = bs.bservice_id AND " +
            "es.date = ?1 AND " +
            "es.start_time >= ?2 AND " +
            "es.end_time <= ?3", nativeQuery = true)
    List<EmployeeSchedule> findSchedulesByDateAndTime(Date date, Date startTime, Date endTime);
}
