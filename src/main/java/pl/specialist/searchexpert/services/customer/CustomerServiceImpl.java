package pl.specialist.searchexpert.services.customer;

import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.exceptions.CustomerAlreadyExistInFavouriteException;
import pl.specialist.searchexpert.exceptions.SpecialistAlreadyExistInFavouriteException;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerIdException;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerNotFoundException;
import pl.specialist.searchexpert.repositories.customer.CustomerRepo;
import pl.specialist.searchexpert.repositories.specialist.SpecialistRepo;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    private final SpecialistRepo specialistRepo;


    public CustomerServiceImpl(CustomerRepo customerRepo, SpecialistRepo specialistRepo) {
        this.customerRepo = customerRepo;
        this.specialistRepo = specialistRepo;
    }


    @Override
    public Customer updateCustomerAccount(Customer customer) {

        Customer existingCustomer = customerRepo.findByCustomerId(customer.getCustomerId());
        if (existingCustomer.getCustomerId() == null) throw new CustomerNotFoundException("Cannot update Customer with ID: '" + customer.getCustomerId() + "' because doesn't exist");
        else {
            if (!customer.getMail().equals(existingCustomer.getMail())) throw new CustomerIdException("Cannot change email address");
            if (!customer.getNickname().equals(existingCustomer.getNickname())) throw new CustomerIdException("Cannot change nickname");
            if (!customer.getPhoneNumber().equals(existingCustomer.getPhoneNumber())) throw new CustomerIdException("Cannot change phone Number");
        }
        return customerRepo.save(customer);
    }

    public Set<Specialist> updateSpecialistFavoriteSet(Set<Specialist> setBeforeAddNewSpecialist, Specialist newestFavoriteSpecialist){
        if(setBeforeAddNewSpecialist.contains(newestFavoriteSpecialist)) throw new SpecialistAlreadyExistInFavouriteException("Specialist already exist in Your Favourite List");
        setBeforeAddNewSpecialist.add(newestFavoriteSpecialist);
        return setBeforeAddNewSpecialist;
    }

    public Set<Customer> updateMarkedByCustomersSet(Set<Customer> setBeforeAddedByCustomer, Customer newestMarkedByCustomer){
        if(setBeforeAddedByCustomer.contains(newestMarkedByCustomer)) throw new CustomerAlreadyExistInFavouriteException("Customer already exist in Your Marked Favourite List");
        setBeforeAddedByCustomer.add(newestMarkedByCustomer);
        return setBeforeAddedByCustomer;
    }


    /*Specialist*/
    @Override
    public void addSpecialistToFavorite(String specialistId,String customerId){
       Customer customer = customerRepo.findByCustomerId(customerId);
       Specialist newestSpecialist = specialistRepo.findBySpecialistId(specialistId);
       customer.setMarkedSpecialists(updateSpecialistFavoriteSet(customer.getMarkedSpecialists(),newestSpecialist));
       newestSpecialist.setMarkedByCustomers(updateMarkedByCustomersSet(newestSpecialist.getMarkedByCustomers(),customer));
    }

    @Override
    public void deleteSpecialistFromFavourite(String customerId, String specialistId){
        customerRepo.findByCustomerId(customerId).getMarkedSpecialists().remove(specialistRepo.findBySpecialistId(specialistId));
        specialistRepo.findBySpecialistId(specialistId).getMarkedByCustomers().remove(customerRepo.findByCustomerId(customerId));
    }

    public void deleteCustomerFromAllSpecialistFavouriteLists(Set<Specialist> setOfAllSpecialistWhichMarkedByConcreteCustomer,String customerId){
        for(Specialist spec : setOfAllSpecialistWhichMarkedByConcreteCustomer){
            spec.getMarkedByCustomers().remove(customerRepo.findByCustomerId(customerId));
        }
        setOfAllSpecialistWhichMarkedByConcreteCustomer.clear();
    }

    @Override
    public void deleteCustomerByCustomerId(String customerId){
        Customer customer = customerRepo.findByCustomerId(customerId);
        if (customer == null) throw new CustomerIdException("Cannot Delete Customer with ID: '" + customerId + "' because doesn't exist");
        deleteCustomerFromAllSpecialistFavouriteLists(customer.getMarkedSpecialists(),customerId);
        customerRepo.delete(customer);
    }


    @Override
    public Customer findCustomerById(String customerId) {
        if (customerRepo.count() == 0) throw new CustomerNotFoundException("Any Customer isn't exist");
        Customer customer = customerRepo.findByCustomerId(customerId);
        if (customer == null) throw new CustomerIdException("Customer with ID: '" + customerId + "' doesn't exist");
        return customer;
    }

    @Override
    public Customer findCustomerByMail(String mail) {
        if (customerRepo.count() == 0) throw new CustomerNotFoundException("Any Customer isn't exist");
        Customer customer = customerRepo.findByMail(mail);
        if (customer == null) throw new CustomerIdException("Customer with email: '" + mail + "' doesn't exist");
        return customer;
    }

    @Override
    public Customer findCustomerByNickname(String nickname) {
        if (customerRepo.count() == 0) throw new CustomerNotFoundException("Any Customer isn't exist");
        Customer customer = customerRepo.findByNickname(nickname);
        if (customer == null) throw new CustomerIdException("Customer with nickname: '" + nickname + "' doesn't exist");
        return customer;
    }

    @Override
    public Iterable<Customer> findAllCustomers() {
        if (customerRepo.count() == 0) throw new CustomerNotFoundException("Any Customer isn't exist");
        return customerRepo.findAll();
    }


}