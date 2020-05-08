package pl.specialist.searchexpert.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.payload.login.JWTLoginSuccessResponse;
import pl.specialist.searchexpert.payload.login.LoginRequest;
import pl.specialist.searchexpert.payload.register.ApiResponse;
import pl.specialist.searchexpert.services.auth.AuthServiceImpl;
import pl.specialist.searchexpert.services.MapValidationErrorService;


import javax.validation.Valid;

import java.net.URI;


@Validated
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final MapValidationErrorService mapValidationErrorService;

    private AuthServiceImpl authServiceImpl;

    public AuthController(MapValidationErrorService mapValidationErrorService, AuthServiceImpl authServiceImpl) {
        this.mapValidationErrorService = mapValidationErrorService;
        this.authServiceImpl = authServiceImpl;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        String jwt = authServiceImpl.login(loginRequest);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true,jwt));
    }


    @PostMapping("/register/customer")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Customer customer, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Customer cust = authServiceImpl.registerCustomerAccount(customer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/auth/")
                .buildAndExpand(cust.getMail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true,"Confirm token from Mail now"));

    }

    @PostMapping("/register/customer/confirm")
    public ResponseEntity<?> registerCustomerConfirmation(@RequestParam("token") String confirmationToken){

        authServiceImpl.confirmCustomerAccount(confirmationToken);

        return ResponseEntity.ok("Customer Register Successfully");

    }

    @PostMapping("/register/specialist")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Specialist specialist, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Specialist spec = authServiceImpl.registerSpecialistAccount(specialist);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/auth/")
                .buildAndExpand(spec.getMail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true,"Confirm token from Mail now"));

    }

    @PostMapping("/register/specialist/confirm")
    public ResponseEntity<?> registerSpecialistConfirmation(@RequestParam("token") String confirmationToken){

        authServiceImpl.confirmSpecialistAccount(confirmationToken);

        return ResponseEntity.ok("Specialist Register Successfully");

    }


}
