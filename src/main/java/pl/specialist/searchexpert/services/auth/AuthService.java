package pl.specialist.searchexpert.services.auth;

import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.payload.login.LoginRequest;


public interface AuthService {

    Customer registerCustomerAccount(Customer cust);
    void confirmCustomerAccount(String confirmationToken);
    Specialist registerSpecialistAccount(Specialist spec);
    void confirmSpecialistAccount(String confirmationToken);
    String login(LoginRequest loginRequest);

}
