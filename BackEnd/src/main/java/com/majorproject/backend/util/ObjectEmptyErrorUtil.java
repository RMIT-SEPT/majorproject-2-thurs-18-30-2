package com.majorproject.backend.util;

import com.majorproject.backend.exceptions.ResponseException;
import org.springframework.http.HttpStatus;

/**
 * This class checks if an object is null, for duplication purposes
 */
public class ObjectEmptyErrorUtil {
    public void checkIfNull(Object object, String errorMessage) {
        if(object == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }
}
