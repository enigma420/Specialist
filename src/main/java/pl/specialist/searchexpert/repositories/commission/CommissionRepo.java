package pl.specialist.searchexpert.repositories.commission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.commission.Commission;

@Repository
public interface CommissionRepo extends JpaRepository<Commission,String> {
}
