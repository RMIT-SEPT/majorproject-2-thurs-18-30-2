package com.majorproject.backend.web;

import com.majorproject.backend.models.Services;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/service")
@CrossOrigin
public class ServiceController {

    @Autowired
    ServiceService serviceService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createService(@Valid @RequestBody Services service, BindingResult result) {
        ResponseEntity<?> response;

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) {
            response = errorMap;
        } else {
            Services serviceNew = serviceService.saveOrUpdateService(service);
            response = new ResponseEntity<Services>(service, HttpStatus.CREATED);
        }

        return response;
    }
}
