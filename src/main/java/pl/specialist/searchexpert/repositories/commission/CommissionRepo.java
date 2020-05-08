package pl.specialist.searchexpert.repositories.commission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.customer.Customer;

import java.util.HashSet;

@Repository
public interface CommissionRepo extends JpaRepository<Commission,String> {

    Commission findByCommissionId(String commissionId);
    HashSet<Commission> findByCity(String city);
    HashSet<Commission> findByProfession(String profession);
//    HashSet<Commission> findByCustomer(Customer customer);

    /*Find SPECIALISTS by personal identity*/
    @Query("SELECT c FROM Commission c WHERE (:profession is null or c.profession = :profession) and (:city is null or c.city = :city)")
    HashSet<Commission> findCommissionsByProfessionAndCity(@Param("profession")String profession, @Param("city")String city);


}
