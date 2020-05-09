package pl.specialist.searchexpert.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.repositories.customer.CustomerRepo;
import pl.specialist.searchexpert.repositories.specialist.SpecialistRepo;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SpecialistRepo specialistRepo;

    private final CustomerRepo customerRepo;

    @Autowired
    public CustomUserDetailsService(SpecialistRepo specialistRepo, CustomerRepo customerRepo) {
        this.specialistRepo = specialistRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Specialist spec = specialistRepo.findByMail(username);
        Customer cust = customerRepo.findByMail(username);
        if(spec != null){
            return spec;
        }else if(cust != null){
            return cust;
        }else throw new UsernameNotFoundException("User not found");
    }


    public UserDetails loadUserById(String id){
        if(specialistRepo.findBySpecialistId(id) != null){
            return specialistRepo.findBySpecialistId(id);
        }else if(customerRepo.findByCustomerId(id) != null){
            return customerRepo.findByCustomerId(id);
        }else throw new  UsernameNotFoundException("(loadUserById): User not found");
    }
}
