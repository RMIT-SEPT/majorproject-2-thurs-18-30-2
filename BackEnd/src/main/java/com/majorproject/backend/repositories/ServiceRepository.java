package com.majorproject.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.majorproject.backend.models.Service;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {

    @Override
    Iterable<Service> findAllById(Iterable<Long> iterable);
}
