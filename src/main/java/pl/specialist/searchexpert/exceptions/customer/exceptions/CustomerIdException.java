package pl.specialist.searchexpert.exceptions.customer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CustomerIdException extends RuntimeException{

    public CustomerIdException(String message) {
        super(message);
    }

}