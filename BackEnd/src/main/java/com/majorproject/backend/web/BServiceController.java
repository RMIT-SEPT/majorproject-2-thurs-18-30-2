package com.majorproject.backend.web;

import com.majorproject.backend.models.BService;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.services.BServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/bService")
@CrossOrigin
public class BServiceController {

    @Autowired
    BServiceService bServiceService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

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
}
