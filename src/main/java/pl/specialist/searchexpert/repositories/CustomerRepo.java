package pl.specialist.searchexpert.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.customer.Customer;


import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,String> {

    @Override
    long count();

    @Override
    <S extends Customer> S save(S s);

    @Override
    List<Customer> findAll();

    @Override
    void delete(Customer specialist);

    Customer findByCustomerId(String id);
    Customer findByMail(String mail);
    Customer findByNickname(String nickname);

}
