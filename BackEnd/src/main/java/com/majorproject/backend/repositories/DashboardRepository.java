package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Booking;
import com.majorproject.backend.models.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends CrudRepository<Booking, Long> {

//    @Query(value = "SELECT * FROM CUSTOMER c WHERE c.username = ?1",
//            nativeQuery = true)
//    User updateDashboard(String username);
}
