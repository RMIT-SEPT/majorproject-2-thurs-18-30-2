package com.majorproject.backend.services;

import com.majorproject.backend.models.BService;
import com.majorproject.backend.repositories.BServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class BServiceService {
    @Autowired
    private BServicesRepository BServicesRepository;

    public BService saveOrUpdateBService(BService bService) {
        BService bServiceNew = null;
        bServiceNew = BServicesRepository.save(bService);

        return bServiceNew;
    }
}
