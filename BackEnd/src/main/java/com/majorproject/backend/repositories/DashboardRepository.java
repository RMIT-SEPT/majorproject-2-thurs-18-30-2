package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends CrudRepository<Booking, Long> {

}
