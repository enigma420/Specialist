package pl.specialist.searchexpert.exceptions.commission.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CommissionIdException  extends RuntimeException{

    public CommissionIdException(String message) {
        super(message);
    }
}
