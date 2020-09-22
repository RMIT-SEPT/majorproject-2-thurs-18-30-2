package com.majorproject.backend.services;

import com.majorproject.backend.models.BService;
import com.majorproject.backend.repositories.BServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class BServiceService {
    @Autowired
    private BServiceRepository BServiceRepository;

    /**
     * Creates the bService
     * @param bService The bService
     * @return The bService
     */
    public BService saveOrUpdateBService(BService bService) {
        BService bServiceNew = null;
        bServiceNew = BServiceRepository.save(bService);

        return bServiceNew;
    }
}
