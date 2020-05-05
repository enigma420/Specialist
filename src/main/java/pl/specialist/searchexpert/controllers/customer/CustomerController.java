package pl.specialist.searchexpert.controllers.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.services.MapValidationErrorService;
import pl.specialist.searchexpert.services.customer.CustomerServiceImpl;
import pl.specialist.searchexpert.services.specialist.SpecialistServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("api/customer")
@CrossOrigin
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    private final SpecialistServiceImpl specialistServiceImpl;

    private final MapValidationErrorService mapValidationErrorService;

    public CustomerController(CustomerServiceImpl customerServiceImpl, SpecialistServiceImpl specialistServiceImpl, MapValidationErrorService mapValidationErrorService) {
        this.customerServiceImpl = customerServiceImpl;
        this.specialistServiceImpl = specialistServiceImpl;
        this.mapValidationErrorService = mapValidationErrorService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<?> createCustomerAccount(@Valid @RequestBody Customer customer, BindingResult bindingResult){
//
//        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
//        if(errorMap != null) return errorMap;
//
//        Customer newCustomer = customerServiceImpl.createCustomerAccount(customer);
//        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
//    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCustomerAccount(@Valid @RequestBody Customer customer, BindingResult bindingResult){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

        Customer existingCustomer = customerServiceImpl.updateCustomerAccount(customer);
        return new ResponseEntity<>(existingCustomer,HttpStatus.OK);
    }

    /*Specialist*/
    @PostMapping("/addSpec/{specialistId}")
    public ResponseEntity<?> addSpecialistToFavorite(@Valid @RequestBody Customer customer, @PathVariable("specialistId") String specialistId, BindingResult bindingResult){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

         Customer existingCustomer = customerServiceImpl.addSpecialistToFavorite(specialistServiceImpl.findSpecialistById(specialistId),customer);

        return new ResponseEntity<>(existingCustomer,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") String customerId){
        customerServiceImpl.deleteCustomerByCustomerId(customerId);
        return new ResponseEntity<>("Customer with ID: '" + customerId + "' was deleted",HttpStatus.OK);
    }


    @GetMapping("/getById/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable("customerId") String customerId){

        Customer customer = customerServiceImpl.findCustomerById(customerId);

        return new ResponseEntity<>(customer,HttpStatus.OK);
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
