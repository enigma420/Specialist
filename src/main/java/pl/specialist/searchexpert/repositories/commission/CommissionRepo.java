package pl.specialist.searchexpert.repositories.commission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.customer.Customer;

import java.util.HashSet;
import java.util.List;

@Repository
public interface CommissionRepo extends JpaRepository<Commission,Long> {

    @Override
    long count();

    @Override
    <S extends Commission> S save(S c);

    @Override
    List<Commission> findAll();

    @Override
    void delete(Commission commission);

    Commission findByCommissionId(String commissionId);

    Commission findByTitle(String title);
    HashSet<Commission> findCommissionsByCity(String city);
    HashSet<Commission> findCommissionsByProfession(String profession);
    HashSet<Commission> findCommissionsByCommissionAuthorNickname(String nickname);




}
