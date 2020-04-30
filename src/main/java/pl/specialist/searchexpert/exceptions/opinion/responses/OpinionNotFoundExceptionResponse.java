package pl.specialist.searchexpert.exceptions.opinion.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class OpinionNotFoundExceptionResponse  {

    private String opinionNotFound;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;

    public OpinionNotFoundExceptionResponse(String opinionNotFound) {
        this.opinionNotFound = opinionNotFound;
    }

    public String getOpinionNotFound() {
        return opinionNotFound;
    }

    public void setOpinionNotFound(String opinionNotFound) {
        this.opinionNotFound = opinionNotFound;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
