package com.majorproject.backend.services;

import com.majorproject.backend.models.BService;
import com.majorproject.backend.repositories.BServiceRepository;
import com.majorproject.backend.util.DateNowUtil;
import com.majorproject.backend.util.ListEmptyErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


@org.springframework.stereotype.Service
public class BServiceService {
    @Autowired
    private BServiceRepository bServiceRepository;

    private ListEmptyErrorUtil listEmptyErrorUtil = new ListEmptyErrorUtil();
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

    /**
     * Gets a list of all BServices
     * @return A list of all BServices
     */
    public List<BService> getAllBServices() {
        List<BService> bServiceList = bServiceRepository.getAllBServices();
        listEmptyErrorUtil.checkListEmpty(bServiceList, "BServices");

        return bServiceList;
    }

    /**
     * Gets a list of BServices that have schedules that can be booked
     * @return A list of BServices that have schedules that can be booked
     */
    public List<BService> getAllBServicesThatHaveSchedules() {
        Date currDate = dateNowUtil.getCurrentDate();
        Date currTime = dateNowUtil.getCurrentTime();

        List<BService> bServiceList = bServiceRepository.getAllBServicesThatHaveSchedules(currDate, currTime);
        listEmptyErrorUtil.checkListEmpty(bServiceList, "BServices");

        return bServiceList;
    }
}
