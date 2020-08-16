package errorHandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import errorHandler.subErrors.AbstractSubError;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class Error {
    private HttpStatus status;
    private String message;
    private String desc;
    private List<AbstractSubError> subErrors;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    public Error(HttpStatus status, String message, Throwable exception) {
        timestamp = new Date();
        this.status = status;
        this.message = message;
        this.desc = exception.getLocalizedMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }
}
