package pl.specialist.searchexpert.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.repositories.customer.CustomerRepo;
import pl.specialist.searchexpert.repositories.specialist.SpecialistRepo;

import javax.transaction.Transactional;

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
        if(specialistRepo.findByMail(username) != null){
            return specialistRepo.findByMail(username);
        }else if(customerRepo.findByMail(username) != null){
            return customerRepo.findByMail(username);
        }else throw new UsernameNotFoundException("User not found");
    }

    @Transactional
    public UserDetails loadUserById(String id){
        if(specialistRepo.findBySpecialistId(id) != null){
            return specialistRepo.findBySpecialistId(id);
        }else if(customerRepo.findByCustomerId(id) != null){
            return customerRepo.findByCustomerId(id);
        }else throw new  UsernameNotFoundException("User not found");
    }
}
