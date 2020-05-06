package pl.specialist.searchexpert.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistInFavouriteException extends RuntimeException{

    public CustomerAlreadyExistInFavouriteException(String message) {
        super(message);
    }
}
