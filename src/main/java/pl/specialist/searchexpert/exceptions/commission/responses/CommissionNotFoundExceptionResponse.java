package pl.specialist.searchexpert.exceptions.commission.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CommissionNotFoundExceptionResponse {

    private String commissionNotFound;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;

    public CommissionNotFoundExceptionResponse(String commissionNotFound) {
        this.commissionNotFound = commissionNotFound;
    }

    public String getCommissionNotFound() {
        return commissionNotFound;
    }

    public void setCommissionNotFound(String commissionNotFound) {
        this.commissionNotFound = commissionNotFound;
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
