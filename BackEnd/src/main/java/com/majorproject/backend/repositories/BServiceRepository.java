package com.majorproject.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.majorproject.backend.models.BService;

import java.util.Date;
import java.util.List;

@Repository
public interface BServiceRepository extends CrudRepository<BService, Long> {

    @Override
    Iterable<BService> findAllById(Iterable<Long> iterable);

    /**
     * This query returns a bService based on the bService's id
     * @param id The bService id
     * @return A bService if it exists, otherwise null
     */
    @Query(value = "SELECT bs.* FROM BService bs WHERE bs.bservice_id = ?1", nativeQuery = true)
    BService getBServiceById(long id);

    /**
     * This query returns all BServices in the database
     * @return A list of BServices
     */
    @Query(value = "SELECT bs.* FROM BService bs", nativeQuery = true)
    List<BService> getAllBServices();

    /**
     * This query returns a list of BServices that have schedules that can be booked
     * Checks are based on available and current date and time
     * @param currDate The current date
     * @param currTime The current time
     * @return A list of BServices
     */
    @Query(value = "SELECT bs.* " +
            "FROM BService bs, Employee_Schedule es " +
            "WHERE bs.bservice_id = es.bservice_id AND " +
            "es.availability = true AND " +
            "(" +
                "es.date > ?1 OR " +
                "(es.date = ?1 AND es.start_time >= ?2)" +
            ") " +
            "ORDER BY bs.bservice_id", nativeQuery = true)
    List<BService> getAllBServicesThatHaveSchedules(Date currDate, Date currTime);
}
