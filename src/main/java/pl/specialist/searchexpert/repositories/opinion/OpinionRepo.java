package pl.specialist.searchexpert.repositories.opinion;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import java.util.HashSet;

@Repository
public interface OpinionRepo extends JpaRepository<Opinion,String> {


    Opinion findByOpinionId(String opinionId);

    Opinion findByOpinionIdAndCustomer(String opinionId, Customer customer);
    HashSet<Opinion> findOpinionsBySpecialist(Specialist specialist);
    HashSet<Opinion> findOpinionsByCustomer(Customer customer);
    void deleteByCustomerAndSpecialist(Customer customer,Specialist specialist);
    Opinion findByCustomerAndSpecialist(Customer customer, Specialist specialist);


}
