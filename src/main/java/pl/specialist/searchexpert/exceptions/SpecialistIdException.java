package pl.specialist.searchexpert.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class SpecialistIdException extends RuntimeException{

    public SpecialistIdException(String message) {
        super(message);
    }

}
