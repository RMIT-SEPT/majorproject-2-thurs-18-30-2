package com.majorproject.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.majorproject.backend.exceptions.subErrors.AbstractSubError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Date;
import java.util.List;

public class ResponseException extends RuntimeException {
    private HttpStatus status;
    private String message;
    private List<AbstractSubError> subErrors;

    public ResponseException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AbstractSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<AbstractSubError> subErrors) {
        this.subErrors = subErrors;
    }
}
