package pl.specialist.searchexpert.services.customer;

import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerIdException;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerNotFoundException;
import pl.specialist.searchexpert.exceptions.specialist.exceptions.SpecialistNotFoundException;
import pl.specialist.searchexpert.repositories.customer.CustomerRepo;
import pl.specialist.searchexpert.repositories.specialist.SpecialistRepo;

import javax.transaction.Transactional;
import java.util.HashSet;
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


    public boolean checkMarkedSpecialistsSet(Set<Specialist> groupOfSpecialists , String specialistId){
        for(Specialist spec : groupOfSpecialists){
            if(spec.getSpecialistId().equals(specialistId)) return false;
        }
        return true;
    }

    /*Specialist*/
    @Override
    public Customer addSpecialistToFavorite(Specialist specialist,Customer customer){
        if (specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialist isn't exist");
        HashSet<Specialist> newSpecialist = new HashSet<>();
        newSpecialist.add(specialist);
        if(checkMarkedSpecialistsSet(customer.getMarkedSpecialists(),specialist.getSpecialistId())) {
            Set<Specialist> groupOfSpecialists = customer.getMarkedSpecialists();
            groupOfSpecialists.addAll(newSpecialist);
            customer.setMarkedSpecialists(groupOfSpecialists);
        } else throw new CustomerIdException("you have this specialist in your favorite list");
        return customerRepo.save(customer);
    }

    @Override
    public void deleteCustomerByCustomerId(String customerId) {
        if (customerRepo.count() == 0) throw new CustomerNotFoundException("Any Customer isn't exist");
        Customer customer = customerRepo.findByCustomerId(customerId);
        if (customer == null) throw new CustomerIdException("Cannot Delete Customer with ID: '" + customerId + "' because doesn't exist");
        customerRepo.delete(findCustomerById(customerId));
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