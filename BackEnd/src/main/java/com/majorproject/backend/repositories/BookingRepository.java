package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    @Override
    Iterable<Booking> findAllById(Iterable<Long> iterable);

    /**
     * This query returns a list that contains all rows in booking database
     * @return A list based on ^
     */
    @Query(value = "SELECT b.* FROM Booking b", nativeQuery = true)
    List<Booking> getAllBookings();

    /**
     * This query returns a list of bookings that are after the date specified
     * @param afterThisDate The date
     * @return A list of bookings
     */
    @Query(value = "SELECT b.* FROM Booking b, Employee_Schedule es " +
            "WHERE b.employee_schedule_id = es.employee_schedule_id AND " +
            "es.date > ?1 ORDER BY b.created_at", nativeQuery = true)
    List<Booking> getAllBookingsAfter(Date afterThisDate);

    /**
     * This query returns a list of bookings that are before the date specified
     * @param beforeThisDate The date
     * @return A list of bookings
     */
    @Query(value = "SELECT b.* FROM Booking b, Employee_Schedule es " +
            "WHERE b.employee_schedule_id = es.employee_schedule_id AND " +
            "es.date < ?1 ORDER BY b.created_at", nativeQuery = true)
    List<Booking> getAllBookingsBefore(Date beforeThisDate);

    /**
     * This query returns a list of bookings based on the employee's id and date
     * @param employeeId The employee's id
     * @param date The date
     * @return A list based on ^
     */
    @Query(value = "SELECT * FROM Booking b WHERE b.employee_id = ?1 AND b.date = ?2", nativeQuery = true)
    List<Booking> getEmployeeBookingsByDate(long employeeId, Date date);

    /**
     * This query returns a list of bookings based on the employee's id
     * @param employeeId The employee's id
     * @return A list based on ^
     */
    @Query(value = "SELECT b.* FROM Booking b, Employee_Schedule es, Employee e " +
            "WHERE b.employee_schedule_id = es.employee_schedule_id AND " +
            "es.employee_id = e.id AND " +
            "e.id = ?1 ORDER BY b.created_at", nativeQuery = true)
    List<Booking> getAllEmployeeBookings(long employeeId);

    /**
     * This query returns a list of bookings that are after a specified date and employee id
     * @param employeeId The employee id
     * @param afterThisDate The date
     * @return A list of booking
     */
    @Query(value = "SELECT b.* FROM Booking b, Employee_Schedule es, Employee e " +
            "WHERE b.employee_schedule_id = es.employee_schedule_id AND " +
            "es.employee_id = e.id AND " +
            "e.id = ?1 AND es.date > ?2 ORDER BY b.created_at", nativeQuery = true)
    List<Booking> getAllEmployeeBookingsAfter(long employeeId, Date afterThisDate);

    /**
     * This query returns a list of bookings that are before a specified date and employee id
     * @param employeeId The employee id
     * @param beforeThisDate The date
     * @return A list of booking
     */
    @Query(value = "SELECT b.* FROM Booking b, Employee_Schedule es, Employee e " +
            "WHERE b.employee_schedule_id = es.employee_schedule_id AND " +
            "es.employee_id = e.id AND " +
            "e.id = ?1 AND es.date < ?2 ORDER BY b.created_at", nativeQuery = true)
    List<Booking> getAllEmployeeBookingsBefore(long employeeId, Date beforeThisDate);

    /**
     * This query returns a list of bookings based on the customer's id
     * @param customerId The customer's id
     * @return A list based on ^
     */
    @Query(value = "SELECT b.* FROM Booking b WHERE b.customer_id = ?1 ORDER BY b.created_at", nativeQuery = true)
    List<Booking> getAllCustomerBookings(long customerId);

    /**
     * This query returns a booking based on the booking id
     * @param bookingId The booking id
     * @return A booking if it exists, otherwise null
     */
    @Query(value = "SELECt b.* FROM Booking b WHERE b.id = ?1", nativeQuery = true)
    Booking getBookingById(long bookingId);

    /**
     * This query returns a list of bookings that are after a specific date and customer id
     * @param customerId The customer id
     * @param afterThisDate The date
     * @return A list of bookings
     */
    @Query(value = "SELECT * FROM Booking b, Employee_Schedule es " +
            "WHERE b.employee_schedule_id = es.employee_schedule_id AND " +
            "b.customer_id = ?1 AND es.date > ?2 " +
            "ORDER BY b.created_at", nativeQuery = true)
    List<Booking> getAllCustomerBookingsAfter(long customerId, Date afterThisDate);

    /**
     * This query returns a list of bookings that are before a specific date and customer id
     * @param customerId The customer id
     * @param beforeThisDate The date
     * @return A list of bookings
     */
    @Query(value = "SELECT * FROM Booking b, Employee_Schedule es " +
            "WHERE b.employee_schedule_id = es.employee_schedule_id AND " +
            "b.customer_id = ?1 AND es.date < ?2 " +
            "ORDER BY b.created_at", nativeQuery = true)
    List<Booking> getAllCustomerBookingsBefore(long customerId, Date beforeThisDate);
}
