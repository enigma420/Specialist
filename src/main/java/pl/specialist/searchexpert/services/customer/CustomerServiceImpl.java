package pl.specialist.searchexpert.services.customer;

import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerIdException;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerNotFoundException;
import pl.specialist.searchexpert.repositories.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer createCustomerAccount(Customer customer){

        Customer existingCustomerWithThisSameMail = customerRepo.findByMail(customer.getMail());
        if(existingCustomerWithThisSameMail != null) throw new CustomerIdException("Account with this email: '" + customer.getMail() + "' already exist");
        Customer existingCustomerWithThisSameNickname = customerRepo.findByNickname(customer.getNickname());
        if(existingCustomerWithThisSameNickname != null) throw new CustomerIdException("Account with this nickname: '" + customer.getNickname() + "' already exist");

        return customerRepo.save(customer);
    }
    @Override
    public Customer updateCustomerAccount(Customer customer){

        Customer existingCustomer = customerRepo.findByCustomerId(customer.getCustomerId());
        if(existingCustomer.getCustomerId() == null) throw new CustomerNotFoundException("Cannot update Customer with ID: '" + customer.getCustomerId() + "' doesn't exist");
        else{
            if(!customer.getCustomerId().equals(existingCustomer.getCustomerId())) throw new CustomerIdException("Cannot change customerId");
            if(!customer.getMail().equals(existingCustomer.getMail())) throw new CustomerIdException("Cannot change email address");
            if(!customer.getNickname().equals(existingCustomer.getNickname())) throw new CustomerIdException("Cannot change nickname");
            customer.setCustomerId(customerRepo.findByCustomerId(customer.getCustomerId()).getCustomerId());
            customer.setMail(customerRepo.findByMail(customer.getMail()).getMail());
            customer.setNickname(customerRepo.findByNickname(customer.getNickname()).getNickname());
        }
        return customerRepo.save(customer);
    }
    @Override
    public void deleteCustomerByCustomerId(String customerId){

        if(customerRepo.count() == 0) throw new CustomerNotFoundException("Any Customer isn't exist");
        Customer customer = customerRepo.findByCustomerId(customerId);
        if(customer == null) throw new CustomerIdException("Cannot Delete Customer with ID: '" + customerId + "' because doesn't exist");

        customerRepo.delete(findCustomerById(customerId));
    }
    @Override
    public void deleteAllCustomers(){

        if(customerRepo.count() == 0) throw new CustomerNotFoundException("Any Customer isn't exist");
        customerRepo.deleteAll();
    }
    @Override
    public Customer findCustomerById(String customerId){

        if(customerRepo.count() == 0) throw new CustomerNotFoundException("Any Customer isn't exist");
        Customer customer = customerRepo.findByCustomerId(customerId);

        if(customer == null) throw new CustomerIdException("Customer with ID: '" + customerId + "' doesn't exist");
        return customer;
    }
    @Override
    public Customer findCustomerByMail(String mail){

        if(customerRepo.count() == 0) throw new CustomerNotFoundException("Any Customer isn't exist");
        Customer customer = customerRepo.findByMail(mail);

        if(customer == null) throw new CustomerIdException("Customer with email: '" + mail + "' doesn't exist");
        return customer;
    }
    @Override
    public Customer findCustomerByNickname(String nickname){

        if(customerRepo.count() == 0) throw new CustomerNotFoundException("Any Customer isn't exist");
        Customer customer = customerRepo.findByNickname(nickname);

        if(customer == null) throw new CustomerIdException("Customer with nickname: '" + nickname + "' doesn't exist");
        return customer;
    }
    @Override
    public Iterable<Customer> findAllCustomers(){

        if(customerRepo.count() == 0) throw new CustomerNotFoundException("Any Customer isn't exist");
        return customerRepo.findAll();
    }

}
