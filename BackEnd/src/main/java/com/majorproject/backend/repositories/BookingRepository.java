package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    @Override
    Iterable<Booking> findAllById(Iterable<Long> iterable);
}
