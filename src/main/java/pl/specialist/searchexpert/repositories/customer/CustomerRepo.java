package pl.specialist.searchexpert.repositories.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.customer.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,String> {

    Customer findByCustomerId(String id);
    Customer findByMail(String mail);
    Customer findByNickname(String nickname);
    Customer findByPhoneNumber(String phoneNumber);
}
