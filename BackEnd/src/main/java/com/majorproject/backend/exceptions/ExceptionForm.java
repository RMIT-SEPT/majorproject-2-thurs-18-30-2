package com.majorproject.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.majorproject.backend.exceptions.subErrors.AbstractSubError;

import java.util.Date;
import java.util.List;

public class ExceptionForm {
    private String message;
    private List<AbstractSubError> subErrors;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    public ExceptionForm(ResponseException exception) {
        this.message = exception.getMessage();
        this.subErrors = exception.getSubErrors();
        this.timestamp = new Date();
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
