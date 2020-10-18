package com.majorproject.backend.web;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.BService;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.services.BServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/bService")
@CrossOrigin
public class BServiceController {
    @Autowired
    private BServiceService bServiceService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     * Creates the bService
     * @param bService The bService
     * @param result BindingResult
     * @return A response entity of the registered bService
     */
    @PostMapping("/create")
    public ResponseEntity<?> createBService(@Valid @RequestBody BService bService, BindingResult result) {
        ResponseEntity<?> response;

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) {
            response = errorMap;
        } else {
            BService bServiceNew = bServiceService.saveOrUpdateBService(bService);
            response = new ResponseEntity<BService>(bService, HttpStatus.CREATED);
        }

        return response;
    }

    /**
     * Gets all BServices in the database
     * @return All BServices, in a list
     */
    @GetMapping("/getAllBServices")
    public ResponseEntity<?> getAllBServices() {
        List<BService> bServiceList = bServiceService.getAllBServices();
        return new ResponseEntity<List<BService>>(bServiceList, HttpStatus.OK);
    }

    /**
     * Gets all BServices in the database only if there is a schedule that can be booked from that BService
     * Check availability and date
     * @return BServices that only can be booked, in a list
     */
    @GetMapping("/getAllBServices/haveSchedules")
    public ResponseEntity<?> getAllBServicesThatHaveSchedules() {
        List<BService> bServiceList = bServiceService.getAllBServicesThatHaveSchedules();
        return new ResponseEntity<List<BService>>(bServiceList, HttpStatus.OK);
    }
}
