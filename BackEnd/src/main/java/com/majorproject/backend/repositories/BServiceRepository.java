package com.majorproject.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.majorproject.backend.models.BService;

@Repository
public interface BServiceRepository extends CrudRepository<BService, Long> {

    @Override
    Iterable<BService> findAllById(Iterable<Long> iterable);

    @Query(value = "SELECT bs.* FROM BService bs WHERE bs.bservice_id = ?1", nativeQuery = true)
    BService findByBServiceId(long id);
}
