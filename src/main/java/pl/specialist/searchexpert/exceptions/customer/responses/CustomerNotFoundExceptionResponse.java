package pl.specialist.searchexpert.exceptions.customer.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CustomerNotFoundExceptionResponse {

    private String CustomerNotFound;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;


    public CustomerNotFoundExceptionResponse(String customerNotFound) {
        customerNotFound = customerNotFound;
    }

    public String getCustomerNotFound() {
        return CustomerNotFound;
    }

    public void setCustomerNotFound(String customerNotFound) {
        CustomerNotFound = customerNotFound;
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
