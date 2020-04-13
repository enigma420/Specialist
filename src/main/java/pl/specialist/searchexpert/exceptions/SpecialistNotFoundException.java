package pl.specialist.searchexpert.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SpecialistNotFoundException extends RuntimeException{

    public SpecialistNotFoundException(String message) {
        super(message);
    }

}
