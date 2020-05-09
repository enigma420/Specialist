package pl.specialist.searchexpert.services.customer;

import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import java.util.Set;

public interface CustomerService {

    Customer updateCustomerAccount(Customer customer);
    void deleteCustomerByCustomerId(String customerId);
    Customer findCustomerById(String customerId);
    Customer findCustomerByMail(String mail);
    Customer findCustomerByNickname(String nickname);
    Iterable<Customer> findAllCustomers();
    void addSpecialistToFavorite(String specialistId,String customerId);
    void deleteSpecialistFromFavourite(String customerId, String specialistId);

    Set<Specialist> updateSpecialistFavoriteSet(Set<Specialist> setBeforeAddNewSpecialist, Specialist newestFavoriteSpecialist);
    Set<Customer> updateMarkedByCustomersSet(Set<Customer> setBeforeAddedByCustomer, Customer newestMarkedByCustomer);
}
