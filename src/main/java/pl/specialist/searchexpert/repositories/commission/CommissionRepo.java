package pl.specialist.searchexpert.repositories.commission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.customer.Customer;

import java.util.HashSet;

@Repository
public interface CommissionRepo extends JpaRepository<Commission,String> {


    Commission findByCommissionId(String commissionId);
    HashSet<Commission> findByCity(String city);
    HashSet<Commission> findByProfession(String profession);
    HashSet<Commission> findByCustomer(Customer customer);

}
