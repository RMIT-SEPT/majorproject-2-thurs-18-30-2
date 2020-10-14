package com.majorproject.backend.util;

import com.majorproject.backend.exceptions.ResponseException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ListEmptyErrorService {
    public void checkListEmpty(List<?> list, String listDefinition) {
        if(list.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, listDefinition + " empty");
        }
    }
}
