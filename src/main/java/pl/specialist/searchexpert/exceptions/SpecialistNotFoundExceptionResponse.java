package pl.specialist.searchexpert.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class SpecialistNotFoundExceptionResponse {

    private String SpecialistNotFound;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;


    public SpecialistNotFoundExceptionResponse(String specialistNotFound) {
        SpecialistNotFound = specialistNotFound;
    }

    public String getSpecialistNotFound() {
        return SpecialistNotFound;
    }

    public void setSpecialistNotFound(String specialistNotFound) {
        SpecialistNotFound = specialistNotFound;
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


