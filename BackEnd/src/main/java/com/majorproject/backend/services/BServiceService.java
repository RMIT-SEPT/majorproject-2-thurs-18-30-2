package com.majorproject.backend.services;

import com.majorproject.backend.models.BService;
import com.majorproject.backend.repositories.BServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@org.springframework.stereotype.Service
public class BServiceService {
    @Autowired
    private BServiceRepository bServiceRepository;

    /**
     * Creates the bService
     * @param bService The bService
     * @return The bService
     */
    public BService saveOrUpdateBService(BService bService) {
        BService bServiceNew = null;
        bServiceNew = bServiceRepository.save(bService);

        return bServiceNew;
    }

    public List<BService> getAllBServices() {
        return bServiceRepository.getAllBServices();
    }
}
