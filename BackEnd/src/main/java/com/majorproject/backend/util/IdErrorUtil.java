package com.majorproject.backend.util;

import com.majorproject.backend.exceptions.ResponseException;
import org.springframework.http.HttpStatus;

/**
 * This class checks if there is any parsing errors in id, for duplication purpose
 */
public class IdErrorUtil {
    public long idStringToLong(String idAPI) {
        long returnId = -1;

        try {
            returnId = Long.parseLong(idAPI);
        } catch (Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "ID error");
        }

        if(returnId == -1) { // if id no change
            throw new ResponseException(HttpStatus.BAD_REQUEST, "ID error");
        }

        return returnId;
    }
}
