package pl.specialist.searchexpert.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.SpecialistConfirmationToken;

@Repository
public interface SpecialistConfirmationTokenRepo extends JpaRepository<SpecialistConfirmationToken,String> {

    SpecialistConfirmationToken findByConfirmationToken(String confirmationToken);

}
