package pl.specialist.searchexpert.exceptions.opinion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class OpinionIdException  extends RuntimeException{

    public OpinionIdException(String message) {
        super(message);
    }
}
