package pl.specialist.searchexpert.repositories.opinion;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import java.util.HashSet;

@Repository
public interface OpinionRepo extends JpaRepository<Opinion,Long> {



    void deleteAllBySpecialist(Specialist specialist);

    Opinion findByOpinionId(Long opinionId);

    Opinion findByOpinionIdAndCustomer(Long opinionId, Customer customer);
    HashSet<Opinion> findOpinionsBySpecialist(Specialist specialist);
    HashSet<Opinion> findOpinionsByCustomer(Customer customer);

    Opinion findByCustomerAndSpecialist(Customer customer, Specialist specialist);


}
