package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
            "es.date >= ?2 AND " +
            "es.date <= ?3 " +
            "ORDER BY es.date", nativeQuery = true)
    List<EmployeeSchedule> getEmployeeScheduleByEmployeeIdDate(long employeeId, Date date, Date week);

//    @Query(value = "SELECT es.* " +
//            "FROM Employee_Schedule es, BService bs " +
//            "WHERE es.bservice_id = bs.bservice_id AND " +
//            "bs.bservice_id = ?1 " +
//            "ORDER BY es.date", nativeQuery = true)
//    List<EmployeeSchedule> getEmployeeScheduleByBServiceId(long bServiceId);

//    @Query(value = "SELECT es.* " +
//        "FROM Employee_Schedule es, BService bs " +
//        "WHERE es.bservice_id = bs.bservice_id AND " +
//        "bs.bservice_id = ?1 " +
//        "ORDER BY es.date", nativeQuery = true)
    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es " +
            "WHERE es.bservice_id = ?1", nativeQuery = true)
    List<EmployeeSchedule> getEmployeeByBServiceId(long bServiceId);

    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, BService bs " +
            "WHERE es.bservice_id = bs.bservice_id AND " +
            "bs.bservice_id = ?1 AND " +
            "es.date >= ?2 AND " +
            "es.start_time >= ?3 " +
            "ORDER BY es.date", nativeQuery = true)
    List<EmployeeSchedule> getEmployeeScheduleByBServiceIdAndNow(long bServiceId, Date date, Date currTime);

    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es " +
            "WHERE es.employee_id = ?1 AND " +
//            "es.bservice_id = ?2 AND " +
            "es.date = ?2 AND NOT " +
            "(" +
                "(?3 < es.start_time AND ?4 < es.start_time) OR " +
                "(?3 > es.end_time AND ?4 > es.end_time)" +
            ")", nativeQuery = true)
//    List<EmployeeSchedule> getDuplicatedSchedules(long employeeId, long bServiceId, Date date, Date startTime, Date endTime);
    EmployeeSchedule getDuplicatedSchedules(long employeeId, Date date, Date startTime, Date endTime);

    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, BService bs " +
            "WHERE es.bservice_id = bs.bservice_id AND " +
            "bs.bservice_id = ?1 " +
            "ORDER BY es.date", nativeQuery = true)
    List<EmployeeSchedule> getEmployeeScheduleByBServiceId(long bServiceId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Employee_Schedule es " +
            "SET es.availability = false " +
            "WHERE es.employee_schedule_id = ?1", nativeQuery = true)
    void updateEmployeeScheduleAfterBooked(long employeeScheduleId);

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
            "es.bservice_id = bs.bservice_id AND " +
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
            "es.bservice_id = bs.bservice_id AND " +
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
            "es.bservice_id = bs.bservice_id AND " +
            "es.date = ?1 AND " +
            "es.start_time >= ?2 AND " +
            "es.end_time <= ?3", nativeQuery = true)
    List<EmployeeSchedule> findSchedulesByDateAndTime(Date date, Date startTime, Date endTime);
}
