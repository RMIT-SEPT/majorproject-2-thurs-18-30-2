package com.majorproject.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.majorproject.backend.models.Services;

@Repository
public interface ServiceRepository extends CrudRepository<Services, Long> {

    @Override
    Iterable<Services> findAllById(Iterable<Long> iterable);
}
