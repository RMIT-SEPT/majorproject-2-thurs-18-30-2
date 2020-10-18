package com.majorproject.backend.util;

import com.majorproject.backend.exceptions.ResponseException;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * This class checks if the list is empty, for duplication purpose
 */
public class ListEmptyErrorUtil {
    public void checkListEmpty(List<?> list, String listDefinition) {
        if(list.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, listDefinition + " empty");
        }
    }
}
