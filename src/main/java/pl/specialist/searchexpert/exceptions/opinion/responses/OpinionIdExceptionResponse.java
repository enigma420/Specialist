package pl.specialist.searchexpert.exceptions.opinion.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class OpinionIdExceptionResponse {

    private String opinionId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;

    public OpinionIdExceptionResponse(String opinionId) {
        this.opinionId = opinionId;
    }

    public String getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(String opinionId) {
        this.opinionId = opinionId;
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
