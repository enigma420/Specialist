package pl.specialist.searchexpert.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.specialist.searchexpert.exceptions.commission.exceptions.CommissionIdException;
import pl.specialist.searchexpert.exceptions.commission.exceptions.CommissionNotFoundException;
import pl.specialist.searchexpert.exceptions.commission.responses.CommissionIdExceptionResponse;
import pl.specialist.searchexpert.exceptions.commission.responses.CommissionNotFoundExceptionResponse;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerIdException;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerNotFoundException;
import pl.specialist.searchexpert.exceptions.customer.responses.CustomerIdExceptionResponse;
import pl.specialist.searchexpert.exceptions.customer.responses.CustomerNotFoundExceptionResponse;
import pl.specialist.searchexpert.exceptions.opinion.exceptions.OpinionIdException;
import pl.specialist.searchexpert.exceptions.opinion.exceptions.OpinionNotFoundException;
import pl.specialist.searchexpert.exceptions.opinion.responses.OpinionIdExceptionResponse;
import pl.specialist.searchexpert.exceptions.opinion.responses.OpinionNotFoundExceptionResponse;
import pl.specialist.searchexpert.exceptions.specialist.exceptions.SpecialistIdException;
import pl.specialist.searchexpert.exceptions.specialist.responses.SpecialistIdExceptionResponse;
import pl.specialist.searchexpert.exceptions.specialist.exceptions.SpecialistNotFoundException;
import pl.specialist.searchexpert.exceptions.specialist.responses.SpecialistNotFoundExceptionResponse;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /*Specialist*/

    @ExceptionHandler(SpecialistIdException.class)
    public final ResponseEntity<SpecialistIdExceptionResponse> handleSpecialistIdException(SpecialistIdException ex, WebRequest request){
        SpecialistIdExceptionResponse exceptionResponse = new SpecialistIdExceptionResponse(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setStatus(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SpecialistNotFoundException.class)
    public final ResponseEntity<SpecialistNotFoundExceptionResponse> handleSpecialistNotFoundException(SpecialistNotFoundException ex, WebRequest request){
        SpecialistNotFoundExceptionResponse exceptionResponse = new SpecialistNotFoundExceptionResponse(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
    }

    /*Customer*/

    @ExceptionHandler(CustomerIdException.class)
    public final ResponseEntity<CustomerIdExceptionResponse> handleCustomerIdException(CustomerIdException ex, WebRequest request){
        CustomerIdExceptionResponse exceptionResponse = new CustomerIdExceptionResponse(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setStatus(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public final ResponseEntity<CustomerNotFoundExceptionResponse> handleSpecialistNotFoundException(CustomerNotFoundException ex, WebRequest request){
        CustomerNotFoundExceptionResponse exceptionResponse = new CustomerNotFoundExceptionResponse(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
    }

    /*Commission*/

    @ExceptionHandler(CommissionIdException.class)
    public final ResponseEntity<CommissionIdExceptionResponse> handleCommissionIdException(CommissionIdException ex, WebRequest request){
        CommissionIdExceptionResponse exceptionResponse = new CommissionIdExceptionResponse(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setStatus(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(CommissionNotFoundException.class)
    public final ResponseEntity<CommissionNotFoundExceptionResponse> handleCommissionNotFoundException(CommissionNotFoundException ex, WebRequest request){
        CommissionNotFoundExceptionResponse exceptionResponse = new CommissionNotFoundExceptionResponse(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
    }

    /*Commission*/

    @ExceptionHandler(OpinionIdException.class)
    public final ResponseEntity<OpinionIdExceptionResponse> handleOpinionIdException(OpinionIdException ex, WebRequest request){
        OpinionIdExceptionResponse exceptionResponse = new OpinionIdExceptionResponse(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setStatus(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(OpinionNotFoundException.class)
    public final ResponseEntity<OpinionNotFoundExceptionResponse> handleOpinionNotFoundException(OpinionNotFoundException ex, WebRequest request){
        OpinionNotFoundExceptionResponse exceptionResponse = new OpinionNotFoundExceptionResponse(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
    }

}
