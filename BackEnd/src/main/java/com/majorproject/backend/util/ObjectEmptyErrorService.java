package com.majorproject.backend.util;

import com.majorproject.backend.exceptions.ResponseException;
import org.springframework.http.HttpStatus;

public class ObjectEmptyErrorService {
    public void checkIfNull(Object object, String errorMessage) {
        if(object == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }
}
