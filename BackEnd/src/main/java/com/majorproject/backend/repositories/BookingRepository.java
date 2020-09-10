package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    @Override
    Iterable<Booking> findAllById(Iterable<Long> iterable);

    @Query(value = "SELECT TOP 10 * FROM CUSTOMER c WHERE c.username = ?1 ORDER BY desc", nativeQuery = true)
    List<Booking> refreshCustomerDashboard(String username);

    @Query(value = "SELECT TOP 10 * FROM EMPLOYEE e WHERE c.username = ?1 ORDER BY desc", nativeQuery = true)
    List<Booking> refreshEmployeeDashboard(String username);
}
