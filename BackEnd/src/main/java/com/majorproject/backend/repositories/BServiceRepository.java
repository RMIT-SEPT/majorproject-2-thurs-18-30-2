package com.majorproject.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.majorproject.backend.models.BService;

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
    BService findByBServiceId(long id);
}
