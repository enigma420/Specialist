package pl.specialist.searchexpert.services.auth;

import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.payload.login.LoginRequest;


public interface AuthService {

    Customer registerCustomerAccount(Customer cust);
    Specialist registerSpecialistAccount(Specialist spec);
    String login(LoginRequest loginRequest);

}
