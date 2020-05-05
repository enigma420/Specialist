package pl.specialist.searchexpert.exceptions.register;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistUserFieldException extends RuntimeException{

    public AlreadyExistUserFieldException(String message) {
        super(message);
    }
}
