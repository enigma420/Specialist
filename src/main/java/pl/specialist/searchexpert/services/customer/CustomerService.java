package pl.specialist.searchexpert.services.customer;

import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;

public interface CustomerService {

    Customer createCustomerAccount(Customer customer);
    Customer updateCustomerAccount(Customer customer);
    void deleteCustomerByCustomerId(String customerId);
    Customer findCustomerById(String customerId);
    Customer findCustomerByMail(String mail);
    Customer findCustomerByNickname(String nickname);
    Iterable<Customer> findAllCustomers();
    Customer addSpecialistToFavorite(Specialist specialist,Customer customer);
}
