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

    @Query(value = "SELECT b.* FROM Booking b", nativeQuery = true)
    List<Booking> getAllBookings();

    @Query(value = "SELECT * FROM Booking b WHERE b.employee_id = ?1 AND b.date = ?2", nativeQuery = true)
    List<Booking> getEmployeeBookingsByDate(Long id, Date date);

    @Query(value = "SELECT * FROM Booking b WHERE b.customer_id = ?1 ORDER BY b.created_at", nativeQuery = true)
    List<Booking> getAllCustomerBookings(long id);

    @Query(value = "SELECT b.* FROM Booking b, Employee_Schedule es, Employee e " +
            "WHERE b.employee_schedule_id = es.employee_schedule_id AND " +
            "es.employee_id = e.employee_id AND " +
            "e.employee_id = ?1 ORDER BY b.created_at", nativeQuery = true)
    List<Booking> getAllEmployeeBookings(long id);
}
