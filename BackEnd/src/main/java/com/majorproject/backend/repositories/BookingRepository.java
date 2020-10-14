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
     * This query returns a list of bookings based on the customer's id
     * @param customerId The customer's id
     * @return A list based on ^
     */
    @Query(value = "SELECT b.* FROM Booking b WHERE b.customer_id = ?1 ORDER BY b.created_at", nativeQuery = true)
    List<Booking> getAllCustomerBookings(long customerId);

    @Query(value = "SELECt b.* FROM Booking b WHERE b.id = ?1", nativeQuery = true)
    Booking getBookingById(long bookingId);
}
