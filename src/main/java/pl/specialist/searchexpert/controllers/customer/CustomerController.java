package pl.specialist.searchexpert.controllers.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.request.CustomerIdSpecialistIdBody;
import pl.specialist.searchexpert.request.SpecialistIdBody;
import pl.specialist.searchexpert.services.MapValidationErrorService;
import pl.specialist.searchexpert.services.customer.CustomerServiceImpl;
import pl.specialist.searchexpert.services.specialist.SpecialistServiceImpl;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/customer")
@CrossOrigin
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;


    private final MapValidationErrorService mapValidationErrorService;

    public CustomerController(CustomerServiceImpl customerServiceImpl, MapValidationErrorService mapValidationErrorService) {
        this.customerServiceImpl = customerServiceImpl;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCustomerAccount(@Valid @RequestBody Customer customer, BindingResult bindingResult){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

        Customer existingCustomer = customerServiceImpl.updateCustomerAccount(customer);
        return new ResponseEntity<>(existingCustomer,HttpStatus.OK);
    }

    /*Specialist*/
    @PostMapping("/addSpec")
    public ResponseEntity<?> addSpecialistToFavorite(@RequestBody SpecialistIdBody specialistIdBody, Principal principal){
        customerServiceImpl.addSpecialistToFavorite(specialistIdBody.getSpecialistId(),principal.getName());

        return new ResponseEntity<>("Specialist was added to your Favourite Specialist List !",HttpStatus.OK);
    }

    @DeleteMapping("/deleteSpec/{specialistId}")
    public ResponseEntity<?> deleteSpecialistFromCustomerFavorite(@PathVariable("specialistId") String specialistId , Principal principal){
        customerServiceImpl.deleteSpecialistFromFavourite(principal.getName(),specialistId);
        return new ResponseEntity<>("Specialist was removed from Your Favourite Specialist List !",HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(Principal principal){
        customerServiceImpl.deleteCustomerByCustomerId(principal.getName());
        return new ResponseEntity<>("Customer with ID: '" + principal.getName() + "' was deleted",HttpStatus.OK);
    }


    @GetMapping("/getByMail/{mail}")
    public ResponseEntity<?> getCustomerByMail(@PathVariable("mail") String mail){

        Customer customer = customerServiceImpl.findCustomerByMail(mail);

        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    @GetMapping("/getByNickname/{nickname}")
    public ResponseEntity<?> getCustomerByNickname(@PathVariable("nickname") String nickname){

        Customer customer = customerServiceImpl.findCustomerByNickname(nickname);

        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCustomers(){

        Iterable<Customer> allCustomers = customerServiceImpl.findAllCustomers();

        return new ResponseEntity<>(allCustomers,HttpStatus.OK);
    }


}
