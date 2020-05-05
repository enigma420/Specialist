package pl.specialist.searchexpert.exceptions.register;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegisterConfirmFieldException extends RuntimeException {

    public RegisterConfirmFieldException(String message) {
        super(message);
    }
}
