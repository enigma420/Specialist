package pl.specialist.searchexpert.exceptions.opinion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OpinionNotFoundException extends RuntimeException{

    public OpinionNotFoundException(String message) {
        super(message);
    }
}
