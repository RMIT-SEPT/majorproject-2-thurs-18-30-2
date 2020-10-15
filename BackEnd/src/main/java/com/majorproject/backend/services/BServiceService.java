package com.majorproject.backend.services;

import com.majorproject.backend.models.BService;
import com.majorproject.backend.repositories.BServiceRepository;
import com.majorproject.backend.util.DateNowUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


@org.springframework.stereotype.Service
public class BServiceService {
    @Autowired
    private BServiceRepository bServiceRepository;

    private DateNowUtil dateNowUtil = new DateNowUtil();

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
        List<BService> bServiceList = bServiceRepository.getAllBServices();
        return bServiceList;
    }

    public List<BService> getAllBServicesThatHaveSchedules() {
        Date currDate = dateNowUtil.getCurrentDate();
        Date currTime = dateNowUtil.getCurrentTime();

        List<BService> bServiceList = bServiceRepository.getAllBServicesThatHaveSchedules(currDate, currTime);

        return bServiceList;
    }
}
