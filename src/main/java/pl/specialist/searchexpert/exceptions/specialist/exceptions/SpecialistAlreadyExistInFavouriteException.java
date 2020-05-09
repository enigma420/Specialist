package pl.specialist.searchexpert.exceptions.specialist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SpecialistAlreadyExistInFavouriteException extends RuntimeException {

    public SpecialistAlreadyExistInFavouriteException(String message) {
        super(message);
    }
}
