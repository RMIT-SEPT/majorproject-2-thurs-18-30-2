package com.majorproject.backend.services;

import com.majorproject.backend.models.Services;
import com.majorproject.backend.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public Services saveOrUpdateService(Services service) {
        Services serviceNew = null;
        serviceNew = serviceRepository.save(service);

        return serviceNew;
    }
}
